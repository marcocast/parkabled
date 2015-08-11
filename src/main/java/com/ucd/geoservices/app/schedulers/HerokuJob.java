package com.ucd.geoservices.app.schedulers;

import javax.ws.rs.client.ClientBuilder;

import org.springframework.stereotype.Component;

import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.utility.HashMapBuilder;
import com.ucd.geoservices.app.Main;

@Component
public class HerokuJob implements ScheduledJob<HerokuJob> {

	@Override
	public SystemData<String, String> scheduleAndLog() {

		String wadl = ClientBuilder.newClient().target("https://" + Main.APPNAME + ".herokuapp.com")
				.path(Main.APPNAME + "/resources/application.wadl").request().get(String.class);

		return SystemData.<String,String>builder().errors(0).processed(1)
				.dataMap(HashMapBuilder.of("wadl", wadl).build()).build();
	}

}