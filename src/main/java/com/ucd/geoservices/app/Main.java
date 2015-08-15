package com.ucd.geoservices.app;

import java.util.Optional;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;

@Microserver(basePackages = { "com.ucd.geoservices" })
public class Main {

	public static final String APPNAME = Optional.ofNullable(System.getenv("appname")).orElse(System.getProperty("appname"));

	public static final GeoservicesModule GEOSERVICES_MODULE = new GeoservicesModule(APPNAME + "/resources");

	public static void main(String[] args) {

		new MicroserverApp(GEOSERVICES_MODULE).start();

	}

}
