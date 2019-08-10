package com.gk.screenshot.service;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gk.screenshot.dal.Screen;
import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.GenerateScreenShotRequest;
import com.gk.screenshot.model.GetStatusRequest;
import com.gk.screenshot.model.GetStatusResponse;
import com.gk.screenshot.model.Pair;
import com.gk.screenshot.model.RequestStatus;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Service
public class ScreenService {

	@Autowired
	private ScreenRepository screenRepo;
	
	private static boolean done = false;

	

	@PostConstruct
	public void init() {
		ScreenGrabber sg = new ScreenGrabber();
		ScreenGrabber.setScRepo(screenRepo);
		// sg.startApp(new String[2]);
	}

	public GetStatusResponse getStatus(GetStatusRequest reg) {

		GetStatusResponse r = new GetStatusResponse();
		Pair<String, RequestStatus> p = new Pair<String, RequestStatus>("11", RequestStatus.PENDING);
		List<Pair<String, RequestStatus>> ps = new ArrayList<>();
		ps.add(p);
		r.setStatus(ps);
		return r;
	}
	
	private void start() {
		ScreenGrabber sg = new ScreenGrabber();
		ScreenGrabber.setScRepo(screenRepo);
		done=true;
		sg.startApp(new String[2]);
	}

	public void createScreenShot(String customerId, final GenerateScreenShotRequest rq)
			throws URISyntaxException, InterruptedException, IOException {
		System.out.println(done);
		if (!done) {
			 Runnable r = () ->start();
			 new Thread(r).start();			
		}
		List<String> sts = rq.getUrls();
		for (String string : sts) {
			Screen sc = new Screen();
			sc.setRequestUrl(string);
			sc.setRequestUrlDomain(new URI(string).getHost());
			sc.setCreated(new Date());
			sc.setStatus(RequestStatus.PENDING.name());
			// sc.setScreenshot(sg.getRes());
			sc.setCustomerId(customerId);
			screenRepo.save(sc);
		}
	}

}
