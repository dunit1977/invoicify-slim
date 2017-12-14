package com.lmig.gfc.invoicify.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lmig.gfc.invoicify.models.BillingRecord;
import com.lmig.gfc.invoicify.models.Company;
import com.lmig.gfc.invoicify.services.BillingRecordRepository;
import com.lmig.gfc.invoicify.services.CompanyRepository;

@Controller
@RequestMapping("/billing-records")
public class BillingRecordController {
	
	private CompanyRepository companyRepo;
	private BillingRecordRepository billingRecordRepo;
	
	public BillingRecordController(CompanyRepository companyRepo, BillingRecordRepository billingRecordRepo) {
		this.companyRepo = companyRepo;
		this.billingRecordRepo = billingRecordRepo;
	}
	
	@GetMapping("")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("billing-records/list");
		List<BillingRecord> records = billingRecordRepo.findAll();
		// Get all the billing records and add them to the model and view with the key "records"
		List<Company> companies = companyRepo.findAll();
		// Get all the companies and add them to the model and view with the key "companies"
		mv.addObject("records", records);
		mv.addObject("companies",companies);
		return mv;
	}

}
