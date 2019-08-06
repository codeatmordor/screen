package com.gk.screenshot.model;

import java.util.List;

public class GetStatusRequest {
	private List<String> request_ids;
	public List<String> getRequest_ids() {
		return request_ids;
	}
	public void setRequest_ids(List<String> request_ids) {
		this.request_ids = request_ids;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	private String customer_id;
	
}
