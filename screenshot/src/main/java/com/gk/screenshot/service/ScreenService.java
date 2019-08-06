package com.gk.screenshot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.GetStatusRequest;
import com.gk.screenshot.model.GetStatusResponse;
import com.gk.screenshot.model.Pair;
import com.gk.screenshot.model.RequestStatus;

@Service
public class ScreenService {

	@Autowired
	private ScreenRepository screenRepo;
	
	public GetStatusResponse getStatus(GetStatusRequest reg) {
		
		GetStatusResponse r = new GetStatusResponse();
		Pair<String,RequestStatus> p = new Pair<String, RequestStatus>("11", RequestStatus.PENDING);
		List<Pair<String,RequestStatus>> ps = new ArrayList<>();
		ps.add(p);
		r.setStatus(ps);
		return r;
	}
	
	
}
