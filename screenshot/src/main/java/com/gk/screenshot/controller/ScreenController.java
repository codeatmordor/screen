package com.gk.screenshot.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.validation.Valid;


import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.GenerateScreenShotRequest;
import com.gk.screenshot.model.GetStatusRequest;
import com.gk.screenshot.model.GetStatusResponse;
import com.gk.screenshot.model.ScreenShotReponseDto;
import com.gk.screenshot.service.ScreenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;



@RestController
//@Api(value="Screen Management System", description="Operations pertaining to Screen in Screen Management System")
public class ScreenController {
	@Autowired
	ScreenService ss;
	
	@Autowired
	ScreenRepository screenRepository;


	@ApiOperation(value = "View a list of available employees", response = GetStatusResponse.class)
	@PostMapping("/api/screens")
	public ResponseEntity<ScreenShotReponseDto> getStatus(@RequestHeader(value= "customer_id" ,required=true) String customerId,@Valid @RequestBody GetStatusRequest r) {
	
		return new ResponseEntity<ScreenShotReponseDto>(ss.getStatus(customerId,r), HttpStatus.OK);
	}
	
	@PostMapping("/api/screens/snaps")
	public ResponseEntity<ScreenShotReponseDto> create(@RequestHeader(value= "customer_id" ,required=true) String customerId, @Valid @RequestBody GenerateScreenShotRequest r) throws URISyntaxException, InterruptedException, IOException {
		
		return new ResponseEntity<>(ss.createScreenShot(customerId, r), HttpStatus.ACCEPTED);
	}
	
	
}
