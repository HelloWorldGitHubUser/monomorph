package com.lakesidemutual.domain.selfservice;

import java.util.Date;

import org.microserviceapipatterns.domaindrivendesign.DomainEvent;

/**
 * Represents the creation of a policy after an insurance quote has been accepted.
 */
public class PolicyCreatedEvent implements DomainEvent {
	private Date date;
	private Long insuranceQuoteRequestId;
	private String policyId;

	public PolicyCreatedEvent() {
	}

	public PolicyCreatedEvent(Date date, Long insuranceQuoteRequestId, String policyId) {
		this.date = date;
		this.insuranceQuoteRequestId = insuranceQuoteRequestId;
		this.policyId = policyId;
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

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
}
