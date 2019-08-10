package com.gk.screenshot.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

import com.gk.screenshot.dal.Screen;

public class ScreenShotReponseDto implements Serializable{
	
	List<Screen> scs ;
	BufferedImage im;

	public BufferedImage getIm() {
		return im;
	}

	public void setIm(BufferedImage im) {
		this.im = im;
	}

	public List<Screen> getScs() {
		return scs;
	}

	public void setScs(List<Screen> scs) {
		this.scs = scs;
	}

}
