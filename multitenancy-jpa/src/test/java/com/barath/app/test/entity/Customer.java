package com.barath.app.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMER")
public class Customer {
	
	@Id
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	
	@Column(name="CUSTOMER_NAME")
	private String customerName;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Customer(String customerName) {
		super();	
		this.customerName = customerName;
	}

	public Customer() {
		super();
		
	}
	
	
	

	public Customer(Long customerId, String customerName) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + "]";
	}
	
	
	
	

}
