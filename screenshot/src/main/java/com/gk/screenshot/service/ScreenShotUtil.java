package com.gk.screenshot.service;

import java.net.URL;
import java.util.Set;

public class ScreenShotUtil {
	
	public static int count=0;
	
	public static void validateUrl(Set<String> urls) {
		for (String string : urls) {		
			try {
				new URL(string).toURI();
			}catch(Exception e) {
				throw new ScreenShotException("URL "+ string+" is not valid.");
			}
		}
	}

}
