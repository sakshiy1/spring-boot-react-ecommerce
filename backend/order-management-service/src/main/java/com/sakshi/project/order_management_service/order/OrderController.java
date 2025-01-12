package com.sakshi.project.order_management_service.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakshi.project.order_management_service.ProductFeign.ProductCatalogServiceProxy;
import com.sakshi.project.order_management_service.ProductFeign.ProductResponse;
import com.sakshi.project.order_management_service.customer.Customer;
import com.sakshi.project.order_management_service.customer.CustomerRepo;
import com.sakshi.project.order_management_service.exception.CustomerNotFoundException;
import com.sakshi.project.order_management_service.exception.OrderNotFoundException;

import feign.Response;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;

@RestController
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	TotalOrderRepo totalOrderRepo;

	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	ProductCatalogServiceProxy productProxy;

	
	@GetMapping("/api/orders/customerId/{customerId}")
	public ResponseEntity<List<Order>> getOrders(@PathVariable int customerId){
		
		Optional<Customer> customer = customerRepo.findById(customerId);
		
		List<Order> orders = orderRepo.findByCustomer_CustomerId(customerId);
		
		if (orders.isEmpty()) {
		throw new OrderNotFoundException("No orders for this customer found" + customerId);
	}
		
		return ResponseEntity.ok(orders);
		
		
	}

	
	@PostMapping("/api/orders")
	public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> orderRequest) {

		logger.debug("Order Request Body: {}", orderRequest);

		// Fetch customer Id,product Id, quantity
		Map<String, Object> map = (Map<String, Object>) orderRequest.get("customer");
		int customerId = ((Number) map.get("customerId")).intValue();
		Long productId = Long.valueOf(orderRequest.get("productId").toString());
		int quantity = ((Number) orderRequest.get("quantity")).intValue();

		logger.debug("Extracted customerId: {}, productId: {}, quantity: {}", customerId, productId, quantity);

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException("customer do not exist with this id" + customerId));

		//fetch product details
//		ProductDetails productDetails = fetchProductDetails(productId);
		ProductDetails productDetails = fetchProductDetailsFeign(productId);
		logger.debug("Fetching product details for productId: {}", productId);
		
		// Get this details for creating order
		Order order = new Order();
		order.setCustomer(customer);
		order.setProductId(productId);
		order.setQuantity(quantity);
		
		// Check if the product name is not null
		String productName = productDetails.getName();
		if (productName == null) {
			throw new OrderNotFoundException("Product name is null for product id: " + productId);
		}
		double price = productDetails.getPrice();

		order.setProductName(productName);
		order.setPrice(price);
		order.setTotalProductprice(price * quantity);
		order = orderRepo.save(order);
		return ResponseEntity.ok(order);

	}
	
	

	private ProductDetails fetchProductDetails(Long productId) {
//		 TODO Auto-generated method stub
		String productServiceUrl = "http://localhost:8000/api/products";
		RestTemplate restTemplate = new RestTemplate();
		String response;
		try {
			response = restTemplate.getForObject(productServiceUrl, String.class);
		} catch (RestClientException e) {
			logger.error("Error calling product API", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Product service is unavailable", e);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();

		List<ProductDetails> productDetailsList;
		try {
			productDetailsList = objectMapper.readValue(response, new TypeReference<List<ProductDetails>>() {
			});
		} catch (Exception e) {
			logger.error("Error parsing product details response", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error parsing product details response", e);
		}

		logger.debug("Product Details: {} ", productDetailsList);

		if (productDetailsList == null) {

			throw new OrderNotFoundException("Product doesn't exist" + productId);

		}

		return productDetailsList.stream().filter(product -> product.getId() == productId).findFirst()
				.orElseThrow(() -> new OrderNotFoundException("Product not found with id: " + productId));

	}


	private ProductDetails fetchProductDetailsFeign(Long productId) {
		
		List<ProductDetails> products = productProxy.retrieveProducts();
		
		if(products.isEmpty()) {
			throw new OrderNotFoundException("Product does not exist");
		}
		
		return products.stream().filter(product -> product.getId() == productId).findFirst()
		.orElseThrow(()-> new OrderNotFoundException("Product not found with id: " + productId));	
		
	}

	@GetMapping("/api/totalorders/{customerId}")
	public ResponseEntity<TotalOrders> TotalOrdersForCustomer(@PathVariable int customerId) {
		
		Optional<Customer> customer = customerRepo.findById(customerId);

		List<Order> orders = orderRepo.findByCustomer_CustomerId(customerId);

		if (orders.isEmpty()) {
			throw new OrderNotFoundException("no orders for this customer" + customerId);
		}

		double customerTotal = orders.stream().mapToDouble(Order::getTotalProductprice).sum();
		
		TotalOrders totalOrder;
		
		Optional<TotalOrders> existingTotalOrder = totalOrderRepo.findByCustomerId(customerId);
		
		if(existingTotalOrder.isPresent()) {
			totalOrder = existingTotalOrder.get();
			totalOrder.setTotalCustomerBill(customerTotal);	
		}else {
			
		totalOrder = new TotalOrders();
		
		totalOrder.setCustomerId(customerId);
		totalOrder.setCustomerTotal(customerTotal);
		}
		
		TotalOrders save = totalOrderRepo.save(totalOrder);

		return ResponseEntity.ok(save);

	}
	
	@DeleteMapping("/api/orders/orderId/{orderId}")
	public void deleteOrdersForCustomer(@PathVariable Long orderId) {
		
		Optional<Order> saved = orderRepo.findById(orderId);
		if(saved.isPresent()) {
			orderRepo.deleteById(orderId);
		}
		
	}
	

}
