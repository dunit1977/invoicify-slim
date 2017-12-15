package com.lmig.gfc.invoicify.controllers;

import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lmig.gfc.invoicify.models.BillingRecord;
import com.lmig.gfc.invoicify.models.Company;
import com.lmig.gfc.invoicify.models.Invoice;
import com.lmig.gfc.invoicify.models.InvoiceLineItem;
import com.lmig.gfc.invoicify.models.User;
import com.lmig.gfc.invoicify.services.BillingRecordRepository;
import com.lmig.gfc.invoicify.services.CompanyRepository;
import com.lmig.gfc.invoicify.services.InvoiceRepository;

@Controller
@RequestMapping("/invoices")
public class InvoicesController {
	private InvoiceRepository invoiceRepo;
	private CompanyRepository companyRepo;
	private BillingRecordRepository billingRecordRepo;

	public InvoicesController(InvoiceRepository invoiceRepo, CompanyRepository companyRepo, BillingRecordRepository billingRecordRepo) {
		this.invoiceRepo = invoiceRepo;
		this.companyRepo = companyRepo;
		this.billingRecordRepo = billingRecordRepo;
	}

	@GetMapping("")
	public ModelAndView showInvoices() {
		ModelAndView mv = new ModelAndView("invoices/list");
		List<Invoice> invoices = invoiceRepo.findAll();
		// Get all the invoices and add them to the model and view with the key
		// "invoices"
		mv.addObject("invoices", invoices);
		// Add a key to the model and view named "showTable" which should be true if
		// there's more than one invoice and false if there are zero invoices

//		boolean showTable = invoices.isEmpty() ? false : true;
//		boolean isshowTable;
//		if (invoices.isEmpty()) {
//			
//			isshowTable = false;
//		} else {
//			isshowTable = true;
//		}
//		mv.addObject("showTable",isshowTable);
		mv.addObject("showtable",invoices.size()>0);
		return mv;
	}

	@GetMapping("/clients")
	public ModelAndView chooseClient() {
		ModelAndView mv = new ModelAndView("invoices/clients");
		List<Company> clients = companyRepo.findAll();
		// Get all the clients and add them to the model and view with the key "clients"
		mv.addObject("clients", clients);
		return mv;
	}

	@GetMapping("/clients/{clientId}")
	public ModelAndView createInvoice(@PathVariable Long clientId) {
		ModelAndView mv = new ModelAndView("invoices/billing-records-list");
		
		// Get all the billing records for the specified client that have no associated
		// invoice line item and add them with the key "records"
		
//      TOO MANY RECORDS!!!
//		List<BillingRecord> allOfTheTooManyRecords = billingRecordRepo.findAll();
//		ArrayList<BillingRecord> clientRecords = new ArrayList<BillingRecord>();
//		for (BillingRecord record : allOfTheTooManyRecords) {
//			if (record.getClient().getId() == clientId && record.getLineItem() == null) {
//				clientRecords.add(record);
//			}
//		}
		
		List<BillingRecord> clientRecords = billingRecordRepo.findByClientIdAndLineItemIsNull(clientId);
		mv.addObject("records", clientRecords);
		
		// Add the client id to the model and view with the key "clientId"
		mv.addObject("clientId", clientId);

		return mv;
	}

	@PostMapping("/clients/{clientId}")
	public String createInvoice(Invoice invoice, @PathVariable Long clientId, long[] recordIds, Authentication auth) {
		// Get the user from the auth.getPrincipal() method
		User user = (User) auth.getPrincipal();
		// Find all billing records in the recordIds array
		List<BillingRecord> billingRecords = new ArrayList<BillingRecord>();
		for (long id : recordIds) {
			billingRecords.add(billingRecordRepo.findOne(id));
		}
		
		
//		****** here's where I stopped****** List<BillingRecord> invoices = Invoice.findAll(long [<recordIds>]);
//		List<BillingRecord> billingRecords = billingRecordRepo.findAll();
		// Create a new list that can hold invoice line items
//		****** here's where I stopped******	List<InvoiceLineItems> invoices = invoiceRepo.
		List<InvoiceLineItem> invoiceitemList = new ArrayList<InvoiceLineItem>();
		// For each billing record in the records found from recordIds
		for (BillingRecord billingRecord : billingRecords) {
		// Create a new invoice line item
			InvoiceLineItem invoiceLineItem = new InvoiceLineItem();
		// Set the billing record on the invoice line item
			invoiceLineItem.setBillingRecord(billingRecord);
		// Set the created by to the user
			invoiceLineItem.setCreatedBy(user);
		// Set the invoice on the invoice line item
			invoiceLineItem.setInvoice(invoice);
		// Add the invoice line item to the list of invoice line items
			invoiceitemList.add(invoiceLineItem);
			invoice.getLineitems().add(invoiceLineItem);
		}
		// Set the list of line items on the invoice
		invoice.setLineitems(invoiceitemList);
		// Set the created by on the invoice to the user
		invoice.setCreatedBy(user);
		// Set the client on the invoice to the company identified by clientId
		Company company = companyRepo.findOne(clientId);
		invoice.setCompany(company);
		// Save the invoice to the database
		invoiceRepo.save(invoice);
		return "redirect:/invoices";
	}

}


