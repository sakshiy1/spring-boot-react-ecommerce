package com.sakshi.project.order_management_service.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sakshi.project.order_management_service.exception.CustomerNotFoundException;
import com.sakshi.project.order_management_service.order.OrderController;


@RestController
public class CustomerController {
	
	@Autowired
	CustomerRepo customerRepo;
	
	@PostMapping(value="api/customers" ,consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Customer>  addCustomers(@RequestBody Customer customer) {
		Customer save = customerRepo.save(customer);
		return ResponseEntity.ok(save);
	}
	
	@GetMapping("/api/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer> all =  customerRepo.findAll();
		return ResponseEntity.ok(all);
	}
	
	@GetMapping("/api/customers/{customerId}")
	public EntityModel<Customer> getCustomerById(@PathVariable int customerId){
		
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException("customer do not exist with this id" + customerId));

		EntityModel<Customer> entityModel = EntityModel.of(customer);
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllCustomers());
		entityModel.add(link.withRel("all-customers"));
		
		WebMvcLinkBuilder linkOrders = linkTo(methodOn(OrderController.class).getOrders(customerId));
		entityModel.add(linkOrders.withRel("orders-customers"));
		
		return entityModel;
	}
	
	@DeleteMapping("api/customers/{customerId}")
	public void deleteCustomerById(@PathVariable int customerId) {
		
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException("customer do not exist with this id" + customerId));

		
		customerRepo.deleteById(customerId);
	}

}
