package com.ucd.geoservices.app.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {

	private final HerokuJob herokuJob;

	@Autowired
	public Schedular(HerokuJob herokuJob) {
		this.herokuJob = herokuJob;
	}

	@Scheduled(cron = "1 1,31 8-18 * * *", zone = "GMT")
	public void schedule() {
		herokuJob.scheduleAndLog();
	}
}
