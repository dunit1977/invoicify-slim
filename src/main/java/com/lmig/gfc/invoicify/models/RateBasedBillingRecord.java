package com.lmig.gfc.invoicify.models;

import javax.persistence.Column;
import javax.persistence.Entity;

//This needs to be an entity
@Entity
public class RateBasedBillingRecord extends BillingRecord {
	
	public RateBasedBillingRecord() {}

	public RateBasedBillingRecord(Double rate, Double quantity) {
		super();
		this.rate = rate;
		this.quantity = quantity;
	}

	// This does NOT need an id because it inherits it from the BillingRecord class
	// This needs a double field named rate
	@Column
	private Double rate;
	// This needs a double field named quantity
	@Column
	private Double quantity;

	// This needs to override the getTotal() method and return the product of the
	// rate and quantity

	@Override
	public Double getTotal() {
		return rate * quantity;
	}

	// This needs getters and setters

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

}
