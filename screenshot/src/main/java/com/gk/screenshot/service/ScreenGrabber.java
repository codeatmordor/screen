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
import java.util.Timer;
import java.util.TimerTask;


public class ScreenGrabber extends Application 
{

    private static String url;
    private Webpage browser;
    private byte[] res;
    private Timer timer = new java.util.Timer();
    private static  ScreenRepository scRepo;
    private static int count ;

    public static int getCount() {
		return count;
	}



	public static void setCount(int count) {
		ScreenGrabber.count = count;
	}



	public static ScreenRepository getScRepo() {
		return scRepo;
	}



	public static void setScRepo(ScreenRepository scRepo) {
		ScreenGrabber.scRepo = scRepo;
	}



	public  void startApp(String[] args){
        url = args[0];
        System.setProperty("jsse.enableSNIExtension", "false");
        System.out.println("Creating screenshot for " + url);
        launch(args);
    }


	@Override
	public void start(Stage arg0) throws Exception {
		browser = new Webpage(url);
        monitorPageStatus();
		
	}

	//@Override
    public void start() {
		//Platform.setImplicitExit(false);
        browser = new Webpage(url);
        monitorPageStatus();
    }

    private void monitorPageStatus(){
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if(browser.isPageLoaded()){
                        System.out.println("Page now loaded, taking screenshot...");
                        System.out.println(url);
                        saveAsPng();
                        cancel();
                    }
                    else
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
                        System.out.println("Screenshot saved as " + "abc" + ".png");
                        System.out.println(Arrays.toString(bytes));
                        setRes(bytes);
                        
                    	Screen sc = new Screen();
                    	System.out.println(url);
						/*
						 * sc.setRequestUrl(url); sc.setRequestUrlDomain("Host"); sc.setCreated(new
						 * Date()); sc.setStatus(RequestStatus.COMPLETED.name());
						 * sc.setScreenshot(bytes); sc.setCustomerId("C2"); scRepo.save(sc);
						 */
                        Platform.exit();
                        //System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //System.exit(0);
                    }
                    cancel();
                });
            }
        }, 5000);
		//return null;
    }



	public byte[] getRes() {
		return res;
	}



	public void setRes(byte[] res) {
		this.res = res;
	}




}
