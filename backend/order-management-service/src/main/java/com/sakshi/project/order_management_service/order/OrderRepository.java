package com.sakshi.project.order_management_service.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByCustomer_CustomerId(int customerId);
	
	
	
		
	

}
