package com.ucd.geoservices.app;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.backendless.Backendless;

@Component
public class BackendlessInitializer {

	public BackendlessInitializer() {
		String backendlessAppId = Optional.ofNullable(System.getenv("backendless-application-id")).orElse(
				System.getProperty("backendless-application-id"));
		String backendlessSecretId = Optional.ofNullable(System.getenv("backendless-secret-id")).orElse(System.getProperty("backendless-secret-id"));
		Backendless.initApp(backendlessAppId, backendlessSecretId, "v1");
	}

}
