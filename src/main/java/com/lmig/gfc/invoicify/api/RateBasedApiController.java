package com.lmig.gfc.invoicify.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmig.gfc.invoicify.models.BillingRecord;
import com.lmig.gfc.invoicify.models.User;
import com.lmig.gfc.invoicify.services.BillingRecordRepository;
import com.lmig.gfc.invoicify.services.CompanyRepository;


@RestController
@RequestMapping("api/billing-records/rate-baseds")
public class RateBasedApiController {
	public RateBasedApiController(BillingRecordRepository billingrecordRepository) {
		this.billingrecordRepository = billingrecordRepository;
		this.companyRepository = companyRepository;
	}

	private BillingRecordRepository billingrecordRepository;
	private CompanyRepository companyRepository;



	@PostMapping("")
	public BillingRecord create(@RequestBody BillingRecord record, Authentication auth) {
		User user = (User) auth.getPrincipal();
		record.setCreatedBy(user);
		record.setClient(companyRepository.findOne(record.getClient().getId()));
		return billingrecordRepository.save(record);
	}

}