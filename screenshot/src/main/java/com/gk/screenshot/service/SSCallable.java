package com.gk.screenshot.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class SSCallable implements Callable<String>{
	private WorkerContext dt ;
	private Timer timer;
	private Webpage wp;
	private boolean created = false;

	public SSCallable(WorkerContext dt, Timer timer) {
		super();
		this.dt = dt;
		this.timer = timer;
	}

	@Override
	public String call() throws Exception {
		
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					if(!created) {
						wp = new Webpage(dt.getUrl());
						created = true;
					}
					if (wp.isPageLoaded()) {
						System.out.println("Page is loaded.");
						WritableImage image = wp.snapshot(new SnapshotParameters(), null);
						try {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", baos);
							byte[] bytes = baos.toByteArray();
							System.out.println(Arrays.toString(bytes));
							dt.getSc().setScreenshot(bytes);
							dt.getSc().setStatus("COMPLETED");
							dt.getScRepo().save(dt.getSc());
							Platform.exit();
						} catch (IOException e) {
							e.printStackTrace();
							Platform.exit();
						}
						cancel();
					} else
						System.out.println("Loading page...");
				});
			}
		}, 1, 1000);
		return null;
	}

}
