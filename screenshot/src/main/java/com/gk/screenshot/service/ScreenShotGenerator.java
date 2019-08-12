package com.gk.screenshot.service;

import javafx.application.Application;
import javafx.stage.Stage;
import com.gk.screenshot.dal.Screen;
import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.RequestStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScreenShotGenerator extends Application {

	final CountDownLatch latch1 = new CountDownLatch(1);
	final CountDownLatch latch2 = new CountDownLatch(1);
	private Timer timer = new java.util.Timer();
	private static ScreenRepository scRepo;

	public static ScreenRepository getScRepo() {
		return scRepo;
	}

	public static void setScRepo(ScreenRepository scRepo) {
		ScreenShotGenerator.scRepo = scRepo;
	}

	public static void startApp(String[] args) {
		System.setProperty("jsse.enableSNIExtension", "false");
		launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
			
				while (true) {
					Set<Screen> scs = scRepo.findAllForStatus(RequestStatus.PENDING.name());
					ExecutorService ex = Executors.newFixedThreadPool(scs.size());
					List<Callable<String>> tasks = new ArrayList<>();
					if (scs != null && !scs.isEmpty()) {
						for (Screen sc : scs) {
							WorkerContext dt = new WorkerContext(sc.getRequestUrl(), sc, scRepo);
							tasks.add(new SSCallable(dt,timer));
						}
					}
					try {
						ex.invokeAll(tasks);
						// Snooze for 30 seconds, before polling for next batch.
						Thread.sleep(30000);
						
					} catch (InterruptedException e) { // TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Error -"+ e.getMessage());
					}
				}
			}
		}).start();
	}
}
