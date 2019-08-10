package com.gk.screenshot.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import com.gk.screenshot.dal.Screen;
import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.RequestStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenShotGenerator extends Application {

	private static String url;
	private static String domain;
	private static String customerId;
	private Webpage browser;
	private byte[] res;
	private Timer timer = new java.util.Timer();
	private static ScreenRepository scRepo;
	private static int count;
	private static String reqId;
	private static Screen sc_state;

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		ScreenShotGenerator.count = count;
	}

	public static ScreenRepository getScRepo() {
		return scRepo;
	}

	public static void setScRepo(ScreenRepository scRepo) {
		ScreenShotGenerator.scRepo = scRepo;
	}

	public static void startApp(String[] args) {
		// url = args[0];
		System.setProperty("jsse.enableSNIExtension", "false");
		System.out.println("Creating screenshot for " + url);
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Platform.runLater(() -> {
			while(true) {
			Set<Screen> scs = scRepo.findAllForStatus(RequestStatus.PENDING.name());
			if (scs != null && !scs.isEmpty()) {

				Screen sc = scs.iterator().next();
				// System.out.println(sc.getId());
				browser = new Webpage(sc.getRequestUrl());
				reqId = sc.getId();
				url = sc.getRequestUrl();
				domain = sc.getRequestUrlDomain();
				customerId = sc.getCustomerId();
				sc_state = sc;
				monitorPageStatus();
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sc_state.setScreenshot(res);
				sc_state.setStatus("COMPLETED");
				scRepo.save(sc_state);
				
			}
			}
			/*
			 * try { Thread.sleep(10000); } catch (InterruptedException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		});
		
		Thread.sleep(10000);
		
		
		
	}

	// @Override
	public void start() {
		// Platform.setImplicitExit(false);
		browser = new Webpage(url);
		monitorPageStatus();
	}

	private void monitorPageStatus() {
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					if (browser.isPageLoaded()) {
						System.out.println("Page now loaded, taking screenshot...");
						System.out.println(url);
						saveAsPng();
						cancel();
					} else
						System.out.println("Loading page...");
				});
			}
		}, 1000, 1000);
	}

	@FXML
	private void saveAsPng() {
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					WritableImage image = browser.snapshot(new SnapshotParameters(), null);
					try {
						// ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", baos);
						byte[] bytes = baos.toByteArray();
						res = bytes;
						System.out.println("Request id -" + reqId);
						//Optional<Screen> sc = scRepo.findById(reqId);
						// System.out.println("Size - "+sc1.size());
						//if (sc.isPresent()) {
						/*
						 * System.out.println("Found"); Screen sc1 = new Screen();//sc.get();
						 * //System.out.println(sc1.getId()); sc1.setId(reqId); sc1.setRequestUrl(url);
						 * sc1.setRequestUrlDomain(domain);
						 * sc1.setStatus(RequestStatus.COMPLETED.name()); sc1.setCustomerId(customerId);
						 * sc1.setScreenshot(bytes);
						 */
							//scRepo.save(sc1);
						//}
						/*
						 * sc.setRequestUrl(url); sc.setRequestUrlDomain("Host"); sc.setCreated(new
						 * Date()); sc.setStatus(RequestStatus.COMPLETED.name());
						 * sc.setScreenshot(bytes); sc.setCustomerId("C2"); scRepo.save(sc);
						 */
						Platform.exit();
						// System.exit(0);
					} catch (IOException e) {
						e.printStackTrace();
						// System.exit(0);
					}
					cancel();
				});
			}
		}, 5000);
		// return null;
	}

	public byte[] getRes() {
		return res;
	}

	public void setRes(byte[] res) {
		this.res = res;
	}

}
