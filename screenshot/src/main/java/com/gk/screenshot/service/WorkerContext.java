package com.gk.screenshot.service;

import com.gk.screenshot.dal.Screen;
import com.gk.screenshot.dal.ScreenRepository;

public class WorkerContext {
	private String url;
	private Screen screen;
	private ScreenRepository scRepo;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public WorkerContext(String url, Screen sc, ScreenRepository scRepo) {
		super();
		this.url = url;
		this.screen = sc;
		this.scRepo = scRepo;
	}

	public Screen getSc() {
		return screen;
	}

	public void setSc(Screen sc) {
		this.screen = sc;
	}

	public ScreenRepository getScRepo() {
		return scRepo;
	}

	public void setScRepo(ScreenRepository scRepo) {
		this.scRepo = scRepo;
	}
}
