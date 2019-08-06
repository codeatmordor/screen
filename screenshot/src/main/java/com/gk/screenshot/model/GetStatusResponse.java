package com.gk.screenshot.model;

import java.util.List;

public class GetStatusResponse {

	

	private List<Pair<String, RequestStatus>> status;

	public List<Pair<String, RequestStatus>> getStatus() {
		return status;
	}

	public void setStatus(List<Pair<String, RequestStatus>> status) {
		this.status = status;
	}
}
