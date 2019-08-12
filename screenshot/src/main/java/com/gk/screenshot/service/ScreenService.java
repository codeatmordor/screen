package com.gk.screenshot.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gk.screenshot.dal.Screen;
import com.gk.screenshot.dal.ScreenRepository;
import com.gk.screenshot.model.GenerateScreenShotRequest;
import com.gk.screenshot.model.GetStatusRequest;
import com.gk.screenshot.model.RequestStatus;
import com.gk.screenshot.model.ScreenDTO;
import com.gk.screenshot.model.ScreenShotReponseDto;

@Service
public class ScreenService {

	@Autowired
	private ScreenRepository screenRepo;

	private static boolean done = false;

	public ScreenShotReponseDto getStatus(String customerId, GetStatusRequest reg) {
		if (reg.getRequest_ids() != null && !reg.getRequest_ids().isEmpty()) {
			List<Screen> sc_res = screenRepo.findAllById(reg.getRequest_ids());
			List<ScreenDTO> dtos = new ArrayList<>();
			for (Screen screen : sc_res) {
				ScreenDTO dt = transform(screen);
				dtos.add(dt);
			}
			ScreenShotReponseDto res = new ScreenShotReponseDto();
			res.setScs(dtos);
			return res;
		} else {
			throw new ScreenShotException("Invalid Arguments");
		}
	}

	private ScreenDTO transform(Screen sc) {
		ScreenDTO dt = new ScreenDTO();
		dt.setCreated(sc.getCreated());
		dt.setCustomerId(sc.getCustomerId());
		dt.setId(sc.getId());
		dt.setRequestUrl(sc.getRequestUrl());
		dt.setRequestUrlDomain(sc.getRequestUrlDomain());
		dt.setScreenshot(sc.getScreenshot());
		dt.setStatus(sc.getStatus());
		return dt;
	}

	private void start() {
		ScreenShotGenerator sg = new ScreenShotGenerator();
		ScreenShotGenerator.setScRepo(screenRepo);
		done = true;
		sg.startApp(new String[2]);
	}

	public ScreenShotReponseDto createScreenShot(String customerId, final GenerateScreenShotRequest rq) {
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
			try {
				sc.setRequestUrlDomain(new URI(string).getHost());
			} catch (URISyntaxException e) {
				throw new ScreenShotException("Failure during URL parsing.");
			}
			sc.setCreated(new Date());
			sc.setStatus(RequestStatus.PENDING.name());
			sc.setCustomerId(customerId);
			scs.add(sc);
		}
		List<Screen> sc_res = screenRepo.saveAll(scs);
		List<ScreenDTO> dtos = new ArrayList<>();
		for (Screen screen : sc_res) {
			dtos.add(transform(screen));
		}
		ScreenShotReponseDto res = new ScreenShotReponseDto();
		res.setScs(dtos);
		return res;
	}

	public ScreenShotReponseDto getSnap(String customerId, final String url) {
		Set<Screen> scs = screenRepo.findAllForUrl(url, customerId);
		List<ScreenDTO> dtos = new ArrayList<>();
		for (Screen screen : scs) {
			byte[] ss = screen.getScreenshot();
			ScreenDTO dt = transform(screen);
			dtos.add(dt);
		}
		ScreenShotReponseDto res = new ScreenShotReponseDto();
		res.setScs(dtos);
		return res;
	}

}
