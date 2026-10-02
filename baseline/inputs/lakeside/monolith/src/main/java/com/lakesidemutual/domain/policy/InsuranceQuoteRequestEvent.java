package com.lakesidemutual.domain.policy;

import java.util.Date;

import org.microserviceapipatterns.domaindrivendesign.DomainEvent;
import com.lakesidemutual.interfaces.dtos.policy.insurancequoterequest.InsuranceQuoteRequestDto;

/**
 * Represents the submission of a new insurance quote request by a customer.
 */
public class InsuranceQuoteRequestEvent implements DomainEvent {
	private Date date;
	private InsuranceQuoteRequestDto insuranceQuoteRequestDto;

	public InsuranceQuoteRequestEvent() {
	}

	public InsuranceQuoteRequestEvent(Date date, InsuranceQuoteRequestDto insuranceQuoteRequestDto) {
		this.date = date;
		this.insuranceQuoteRequestDto = insuranceQuoteRequestDto;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public InsuranceQuoteRequestDto getInsuranceQuoteRequestDto() {
		return insuranceQuoteRequestDto;
	}

	public void setInsuranceQuoteRequestDto(InsuranceQuoteRequestDto insuranceQuoteRequestDto) {
		this.insuranceQuoteRequestDto = insuranceQuoteRequestDto;
	}
}
