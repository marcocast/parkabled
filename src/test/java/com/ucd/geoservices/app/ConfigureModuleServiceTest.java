package com.ucd.geoservices.app;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ConfigureModuleServiceTest {

	private ConfigureModuleService configureModuleService;

	@Before
	public void init() {
		configureModuleService = new ConfigureModuleService();
	}

	@Test
	public void testconfigure() {
		assertThat(configureModuleService.configure().getHost(), is("https://" + System.getProperty("appname") + ".herokuapp.com:"));
		assertThat(configureModuleService.configure().getModule(), is(Main.GEOSERVICES_MODULE));
		assertThat(configureModuleService.configure().getPort(), is(8080));
	}
}
