package com.sakshi.project.order_management_service.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sakshi.project.order_management_service.customer.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_order")
public class Order {

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}

	public double getTotalProductprice() {
		return totalProductprice;
	}

	public void setTotalProductprice(double totalProductprice) {
		this.totalProductprice = totalProductprice;
	}

	public Order(long id, String product, int quantity, double price, List<TotalOrders> orderItem,
			@NotNull(message = "Product Id is required") long productId, Customer customer) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.product_price = price;
		this.productId = productId;
		this.customer = customer;
	}

	public Order(long id, Customer customer, String productName, int quantity, double product_price,
			double totalProductprice, @NotNull(message = "Product Id is required") long productId) {
		super();
		this.id = id;
		this.customer = customer;
		this.productName = productName;
		this.quantity = quantity;
		this.product_price = product_price;
		this.totalProductprice = totalProductprice;
		this.productId = productId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)
	@JsonManagedReference
	private Customer customer; // Reference to Customer entity

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer2) {
		this.customer = customer2;
	}

	private String productName;
	
	private int quantity;

	private double product_price;

	private double totalProductprice;

	// @OneToMany(mappedBy = "order")
//	private List<OrderItem> orderItem;

	@NotNull(message = "Product Id is required")
	private long productId;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return product_price;
	}

	public void setPrice(double price) {
		this.product_price = price;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

}