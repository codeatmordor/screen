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
}
