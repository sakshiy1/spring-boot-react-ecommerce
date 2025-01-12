package com.sakshi.project.order_management_service.order;

import java.util.List;

public class OrderSummary {

	public OrderSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderSummary(List<Order> orders, TotalOrders totalOrder) {
		super();
		this.orders = orders;
		this.totalOrder = totalOrder;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public TotalOrders getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(TotalOrders totalOrder) {
		this.totalOrder = totalOrder;
	}

	private List<Order> orders;
	private TotalOrders totalOrder;

}
