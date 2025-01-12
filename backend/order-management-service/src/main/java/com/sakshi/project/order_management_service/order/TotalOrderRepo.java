package com.sakshi.project.order_management_service.order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalOrderRepo extends JpaRepository<TotalOrders, Long> {
	
	Optional<TotalOrders> findByCustomerId(int customerId);

}
