package com.lmig.gfc.invoicify.models;

import javax.persistence.Column;
import javax.persistence.Entity;

// This needs to be an entity
@Entity
public class FlatFeeBillingRecord extends BillingRecord {
	
	public FlatFeeBillingRecord() {}

	// This does NOT need an id because it inherits it from the BillingRecord class
	// This needs a double field named amount
	public FlatFeeBillingRecord(Double amount) {
		super();
		this.amount = amount;
	}

	@Column
	private Double amount;

	// This needs to override the getTotal() method and return the amount

	@Override
	public Double getTotal() {
		return amount;
	}

	// This needs getters and setters
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
