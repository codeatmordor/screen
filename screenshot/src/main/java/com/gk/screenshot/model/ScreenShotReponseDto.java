package com.gk.screenshot.model;

import java.io.Serializable;
import java.util.List;

public class ScreenShotReponseDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7597209125766661960L;
	List<ScreenDTO> scs ;

	public List<ScreenDTO> getScs() {
		return scs;
	}

	public void setScs(List<ScreenDTO> scs) {
		this.scs = scs;
	}
	
}
