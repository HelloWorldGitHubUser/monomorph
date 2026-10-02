package com.lakesidemutual.domain.selfservice;

import java.util.Date;

import org.microserviceapipatterns.domaindrivendesign.DomainEvent;

/**
 * Represents the expiration of an insurance quote for a specific quote request.
 */
public class InsuranceQuoteExpiredEvent implements DomainEvent {
	private Date date;
	private Long insuranceQuoteRequestId;

	public InsuranceQuoteExpiredEvent() {
	}

	public InsuranceQuoteExpiredEvent(Date date, Long insuranceQuoteRequestId) {
		this.date = date;
		this.insuranceQuoteRequestId = insuranceQuoteRequestId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getInsuranceQuoteRequestId() {
		return insuranceQuoteRequestId;
	}

	public void setInsuranceQuoteRequestId(Long insuranceQuoteRequestId) {
		this.insuranceQuoteRequestId = insuranceQuoteRequestId;
	}
}
