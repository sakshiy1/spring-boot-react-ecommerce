package com.sakshi.project.product_catalog_service.product;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sakshi.project.product_catalog_service.exception.ProductNotFoundException;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class ProductController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductJpaRepository repo;

	@GetMapping("/api/products")
	public ResponseEntity<List<Product>> retrieveProducts() {
		
		logger.info("get Products called");

		List<Product> ListProduct = repo.findAll();
		return ResponseEntity.ok(ListProduct);

	}

	@PostMapping("/api/products")
	public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product) {
		
		Product savedProduct = repo.save(product);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProduct.getId()).toUri();
		return ResponseEntity.created(location).body(savedProduct);

	}
	
	@GetMapping("/api/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		
		 Optional<Product> productOptional = repo.findById(id);
		
		if (productOptional.isEmpty()) 
			throw new ProductNotFoundException("id"+ id);
			
		Product product = productOptional.get();
		
		return ResponseEntity.ok(product);
		
	}
	
//	@PutMapping("/api/products/{id}")
//	public ResponseEntity<Product> updateProductById(@PathVariable Long id, @RequestBody Product productDetails) {
//		
//		 Optional<Product> productOptional = repo.findById(id);
//		
//		if (productOptional.isEmpty()) 
//			throw new ProductNotFoundException("id"+ id);
//		
//		Product product = productOptional.get();
//		
//		product.setName(productDetails.getName());
//		product.setDescription(productDetails.getDescription());
//		product.setPrice(productDetails.getPrice());
//		
//		Product savedProduct = repo.save(product);
//		
//		return ResponseEntity.ok(savedProduct);
//	}
	
	@DeleteMapping("/api/products/{id}")
	public void deleteById(@PathVariable long id) {
		
		repo.deleteById(id);
	}
}
