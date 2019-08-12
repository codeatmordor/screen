package com.gk.screenshot.service;

import java.net.URL;
import java.util.List;

public class ScreenShotUtil {
	

	public static void validateUrl(List<String> urls) {
		for (String string : urls) {		
			try {
				new URL(string).toURI();
			}catch(Exception e) {
				throw new ScreenShotException("URL "+ string+" is not valid.");
			}
		}
	}
	
	public static void validateRequest(List<String> urls) {
		if(urls==null || urls.isEmpty()) {
			throw new ScreenShotException("Request is empty.");
		}else if(urls.size()>1000) {
			throw new ScreenShotException("Request exceeds limit of 1000.");
		}
		validateUrl(urls);
	}
}
