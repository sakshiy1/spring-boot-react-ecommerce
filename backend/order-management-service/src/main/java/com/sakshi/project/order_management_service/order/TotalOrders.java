package com.sakshi.project.order_management_service.order;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class TotalOrders {

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public double getTotalCustomerBill() {
		return totalCustomerBill;
	}

	public void setTotalCustomerBill(double totalCustomerBill) {
		this.totalCustomerBill = totalCustomerBill;
	}

	public TotalOrders() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long customerId;

	private double totalCustomerBill;

	


	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCustomerTotal() {
		return totalCustomerBill;
	}

	public void setCustomerTotal(double totalProductPrice) {
		this.totalCustomerBill = totalProductPrice;
	}

	public TotalOrders(long id, long customerId, double totalProductPrice) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.totalCustomerBill = totalProductPrice;
	}
}
