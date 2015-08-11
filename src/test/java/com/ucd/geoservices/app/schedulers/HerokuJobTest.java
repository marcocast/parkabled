package com.ucd.geoservices.app.schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HerokuJobTest {

	private HerokuJob herokuJob;

	@Before
	public void init() {
		this.herokuJob = new HerokuJob();
	}

	@Test
	public void testscheduleAndLog() {
		assertThat(herokuJob.scheduleAndLog().getErrors(), is(0));
		assertThat(herokuJob.scheduleAndLog().getProcessed(), is(1));

	}

}
