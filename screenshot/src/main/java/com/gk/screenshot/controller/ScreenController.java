package com.gk.screenshot.controller;

import javax.validation.Valid;


import com.gk.screenshot.model.GenerateScreenShotRequest;
import com.gk.screenshot.model.GetStatusRequest;
import com.gk.screenshot.model.ScreenShotReponseDto;
import com.gk.screenshot.service.ScreenService;
import com.gk.screenshot.service.ScreenShotUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ScreenController {
	@Autowired
	ScreenService ss;

	@PostMapping("/api/screens")
	public ResponseEntity<ScreenShotReponseDto> getStatus(@RequestHeader(value= "customer_id" ,required=true) String customerId,@Valid @RequestBody GetStatusRequest r) {
		return new ResponseEntity<ScreenShotReponseDto>(ss.getStatus(customerId,r), HttpStatus.OK);
	}
	
	@PostMapping("/api/screens/snaps")
	public ResponseEntity<ScreenShotReponseDto> create(@RequestHeader(value= "customer_id" ,required=true) String customerId, @Valid @RequestBody GenerateScreenShotRequest r) {
		ScreenShotUtil.validateRequest(r.getUrls());
		return new ResponseEntity<>(ss.createScreenShot(customerId, r), HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path="/api/screens/snaps/{url}")
	public ResponseEntity<ScreenShotReponseDto> getSnap(@RequestHeader(value= "customer_id" ,required=true) String customerId, @PathVariable("url") final String url){
		return new ResponseEntity<>(ss.getSnap(customerId, url), HttpStatus.OK);
	}
}
