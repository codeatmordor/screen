package com.gk.screenshot.controller;

import javax.validation.Valid;


import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.GetStatusRequest;
import com.gk.screenshot.model.GetStatusResponse;
import com.gk.screenshot.service.ScreenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ScreenController {
	@Autowired
	ScreenService cricketerService;
	
	@Autowired
	ScreenRepository screenRepository;


	@PostMapping("/api/screens")
	public ResponseEntity<GetStatusResponse> getAllCricketers(@Valid @RequestBody GetStatusRequest r) {
	
		return new ResponseEntity<GetStatusResponse>(cricketerService.getStatus(r), HttpStatus.OK);
	}
	
	
}
