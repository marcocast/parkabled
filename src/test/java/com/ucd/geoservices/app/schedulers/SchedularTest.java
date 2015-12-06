package com.ucd.geoservices.app.schedulers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SchedularTest {
	
	private Schedular schedular;
	
	@Mock
	private HerokuJob herokuJob;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		schedular = new Schedular(herokuJob);
	}
	
	@Test
	public void testSchedule(){
		schedular.schedule();
		Mockito.verify(herokuJob).scheduleAndLog();
	}
	

}
