package com.ucd.geoservices.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.rest.client.nio.RestClient;

public class MainTest {

	private MicroserverApp server;
	private final RestClient<String> rest = new RestClient(1000, 1000).withAccept("application/xml");

	@Before
	public void startServer() {
		server = new MicroserverApp(Main.GEOSERVICES_MODULE);
		server.start();
	}

	@Test
	public void basicEndPoint() {
		assertThat(rest.get("http://localhost:8080/" + System.getProperty("appname") + "/resources/application.wadl")
				.join(), is(not(nullValue())));
	}

	@After
	public void stopServer() {
		server.stop();
	}

}
