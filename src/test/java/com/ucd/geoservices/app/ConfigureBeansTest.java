package com.ucd.geoservices.app;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ucd.geoservices.auth.AuthManager;
import com.ucd.geoservices.data.DataManager;
import com.ucd.geoservices.geo.GeoManager;

public class ConfigureBeansTest {

	private ConfigureBeans configureBeans;

	@Before
	public void init() {
		configureBeans = new ConfigureBeans();

	}

	@Test
	public void testauthManager() {
		assertThat(configureBeans.authManager() instanceof AuthManager, is(true));
	}

	@Test
	public void testgeoManager() {
		assertThat(configureBeans.geoManager() instanceof GeoManager, is(true));
	}

	@Test
	public void testdataManager() {
		assertThat(configureBeans.dataManager() instanceof DataManager, is(true));
	}
}
