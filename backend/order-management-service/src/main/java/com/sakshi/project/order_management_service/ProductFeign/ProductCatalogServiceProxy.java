package com.sakshi.project.order_management_service.ProductFeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;

import com.sakshi.project.order_management_service.order.ProductDetails;

@FeignClient(name = "product-catalog-service", url = "localhost:8000")
public interface ProductCatalogServiceProxy {

	@GetMapping("/api/products")
	public List<ProductDetails> retrieveProducts();
}
