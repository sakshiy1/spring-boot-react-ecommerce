package com.sakshi.project.order_management_service.customer;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sakshi.project.order_management_service.order.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer {



	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(long customerId, @NotNull(message = "customer name is required") String customerName,
			List<Order> orders) {
		super();
		
		this.customerId = customerId;
		this.customerName = customerName;
		this.orders = orders;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerId;

	@NotNull(message = "customer name is required")
	private String customerName;

	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	private List<Order> orders;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
}
