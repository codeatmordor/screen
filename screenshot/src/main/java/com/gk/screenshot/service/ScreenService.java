package com.gk.screenshot.service;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
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
import com.gk.screenshot.model.ScreenShotReponseDto;

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

	public ScreenShotReponseDto getStatus(String customerId, GetStatusRequest reg) {
		if (reg.getRequest_ids() != null && !reg.getRequest_ids().isEmpty()) {
			List<Screen> sc_res = screenRepo.findAllById(reg.getRequest_ids());
			ScreenShotReponseDto res = new ScreenShotReponseDto();
			res.setScs(sc_res);
			try {
				res.setIm(ImageIO.read(new ByteArrayInputStream(sc_res.get(0).getScreenshot())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		} else {
			throw new ScreenShotException("Invalid Arguments");
		}
	}

	private void start() {
		ScreenGrabber sg = new ScreenGrabber();
		ScreenGrabber.setScRepo(screenRepo);
		done = true;
		sg.startApp(new String[2]);
	}

	public ScreenShotReponseDto createScreenShot(String customerId, final GenerateScreenShotRequest rq)
			throws URISyntaxException, InterruptedException, IOException {
		System.out.println(done);
		if (!done) {
			Runnable r = () -> start();
			new Thread(r).start();
		}
		List<String> sts = rq.getUrls();
		List<Screen> scs = new ArrayList<>();
		for (String string : sts) {
			Screen sc = new Screen();
			sc.setRequestUrl(string);
			sc.setRequestUrlDomain(new URI(string).getHost());
			sc.setCreated(new Date());
			sc.setStatus(RequestStatus.PENDING.name());
			// sc.setScreenshot(sg.getRes());
			sc.setCustomerId(customerId);
			scs.add(sc);
		}
		List<Screen> sc_res = screenRepo.saveAll(scs);
		ScreenShotReponseDto res = new ScreenShotReponseDto();
		res.setScs(sc_res);
		return res;
	}

}
