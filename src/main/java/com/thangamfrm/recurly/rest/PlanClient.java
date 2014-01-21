package com.thangamfrm.recurly.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.thangamfrm.recurly.jaxb.Plan;
import com.thangamfrm.recurly.jaxb.Plans;


public class PlanClient extends BaseClient {

	protected String getPlansUrlTemplate = "https://%s.recurly.com/v2/plans";
	protected String readPlanUrlTemplate = "https://%s.recurly.com/v2/plans/%s";
	protected String updatePlanUrlTemplate = "https://%s.recurly.com/v2/plans/%s";
	protected String deletePlanUrlTemplate = "https://%s.recurly.com/v2/plans/%s";
	protected String createPlanUrlTemplate = "https://%s.recurly.com/v2/plans/";

	protected String serviceUrl = null;

	@Value("#{recurlyAPIProperties['recurly.subdomain']}")
	protected String subDomain;

	public PlanClient() {
		super();
	}

	public Plans getPlans() {
		serviceUrl = String.format(getPlansUrlTemplate, subDomain);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.add("Authorization", getAuthorizationKey());

		ResponseEntity<Plans> plans = restTemplate.exchange(serviceUrl, HttpMethod.GET, new HttpEntity<byte[]>(headers), 
				Plans.class);
		return plans.getBody();
	}

	public Plan getPlan(String planCode) {
		serviceUrl = String.format(readPlanUrlTemplate, subDomain, planCode);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.add("Authorization", getAuthorizationKey());

		ResponseEntity<Plan> plan = restTemplate.exchange(serviceUrl, HttpMethod.GET, new HttpEntity<byte[]>(headers), 
				Plan.class);
		return plan.getBody();
	}

	public Plan updatePlan(Plan plan) {
		serviceUrl = String.format(updatePlanUrlTemplate, subDomain, plan.getPlanCode());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.add("Authorization", getAuthorizationKey());
		
		final HttpEntity<Plan> httpEntity = new HttpEntity<Plan>(plan, headers);
		ResponseEntity<Plan> updatedPlan = restTemplate.exchange(serviceUrl, HttpMethod.PUT, httpEntity, Plan.class);
		return updatedPlan.getBody();
	}

	public boolean deletePlan(String planCode) {
		serviceUrl = String.format(deletePlanUrlTemplate, subDomain, planCode);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.add("Authorization", getAuthorizationKey());
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		restTemplate.exchange(serviceUrl, HttpMethod.DELETE, entity, String.class);
		return true;
	}

	public Plan createPlan(Plan plan) {
		serviceUrl = String.format(createPlanUrlTemplate, subDomain);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.add("Authorization", getAuthorizationKey());
		
		final HttpEntity<Plan> httpEntity = new HttpEntity<Plan>(plan, headers);
		ResponseEntity<Plan> response = restTemplate.exchange(serviceUrl, HttpMethod.POST, httpEntity, Plan.class);
			return response.getBody();
	}

}