package com.lmig.gfc.invoicify.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// This needs to be an entity
@Entity
public class Invoice {
	
	public Invoice() {lineitems = new ArrayList<InvoiceLineItem>();}

	public Invoice(Long id, Company company, User createdBy, String invoiceNumber, List<InvoiceLineItem> lineitems) {
		super();
		this.id = id;
		this.company = company;
		this.createdBy = createdBy;
		this.invoiceNumber = invoiceNumber;
		this.lineitems = lineitems;
	}

	// This needs an id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	// This needs a many-to-one relationship to a company named "company"
	@ManyToOne
	private Company company;
	// This needs a many-to-one relationship to a user named "createdBy"
	@ManyToOne
	private User createdBy;
	// This needs a string named "invoiceNumber"
	@Column
	private String invoiceNumber;
	// This needs a one-to-many relationship for a list of invoice line items mapped
	// by "invoice" with a cascade type of ALL
	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<InvoiceLineItem> lineitems;

	// This needs getters and setters

	public Long getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public List<InvoiceLineItem> getLineitems() {
		return lineitems;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setLineitems(List<InvoiceLineItem> lineitems) {
		this.lineitems = lineitems;
	}

}
