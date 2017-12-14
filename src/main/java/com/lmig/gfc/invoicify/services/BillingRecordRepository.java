package com.lmig.gfc.invoicify.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmig.gfc.invoicify.models.BillingRecord;

public interface BillingRecordRepository extends JpaRepository <BillingRecord, Long> {

	// find by client id and lineItem is null
	List<BillingRecord> findByClientIdAndLineItemIsNull(Long clientId);
	
}
