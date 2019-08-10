package com.gk.screenshot.service;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public GetStatusResponse getStatus(GetStatusRequest reg) {

		GetStatusResponse r = new GetStatusResponse();
		Pair<String, RequestStatus> p = new Pair<String, RequestStatus>("11", RequestStatus.PENDING);
		List<Pair<String, RequestStatus>> ps = new ArrayList<>();
		ps.add(p);
		r.setStatus(ps);
		return r;
	}

	public void createScreenShot(String customerId, final GenerateScreenShotRequest rq)
			throws URISyntaxException, InterruptedException, IOException {
		List<String> sts = rq.getUrls();
		for (String string : sts) {
			ScreenGrabber sg = new ScreenGrabber();
			sg.setScRepo(screenRepo);
			String[] args = new String[2];
			args[0] = string;
			// sg.startApp(args);

			/*
			 * Runnable r = () -> sg.startApp(args); Thread t = new Thread(r); t.start();
			 * t.join();
			 */

			// sg.startApp(args);

			Screen sc = new Screen();
			sc.setRequestUrl(string);
			sc.setRequestUrlDomain(new URI(string).getHost());
			sc.setCreated(new Date());
			sc.setStatus(RequestStatus.COMPLETED.name());
			sc.setScreenshot(sg.getRes());
			sc.setCustomerId(customerId);
			screenRepo.save(sc);

		}
	}

}
