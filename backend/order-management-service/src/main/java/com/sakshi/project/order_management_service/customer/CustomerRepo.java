package com.sakshi.project.order_management_service.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakshi.project.order_management_service.order.Order;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	//Optional<Customer> findByCustomerId(int customerId);

	//Optional<Customer> findByCustomerId(long customerId);
	

}
