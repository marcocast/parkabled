package com.ucd.geoservices.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ucd.geoservices.auth.AuthManager;
import com.ucd.geoservices.auth.StormpathAuth;
import com.ucd.geoservices.auth.StormpathProvider;
import com.ucd.geoservices.data.BackendlessDataManager;
import com.ucd.geoservices.data.DataManager;
import com.ucd.geoservices.geo.BackendlessGeoManager;
import com.ucd.geoservices.geo.GeoManager;
import com.ucd.geoservices.transformer.LocationTransformer;
import com.ucd.geoservices.transformer.UserTransformer;

@Configuration
public class ConfigureBeans {

	@Value("${acessTokenExpirationInMinutes}")
	private Integer acessTokenExpirationInMinutes;

	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private StormpathProvider stormpathProvider;

	@Bean
	public AuthManager authManager() {
		return new StormpathAuth(userTransformer, stormpathProvider, acessTokenExpirationInMinutes);
	}

	@Value("${maxRadiusBetweenPointsInMeters}")
	private Integer maxRadiusBetweenPointsInMeters;

	@Autowired
	private LocationTransformer geoPointTransformer;

	@Bean
	public GeoManager geoManager() {
		return new BackendlessGeoManager(geoPointTransformer, maxRadiusBetweenPointsInMeters);
	}

	@Bean
	public DataManager dataManager() {
		return new BackendlessDataManager();
	}

}
