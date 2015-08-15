package com.ucd.geoservices.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.geo.GeoManager;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;

@Component
public class LocationService {

	@Autowired
	private GeoManager geoManager;

	public Location addLocation(Location location) {
		return geoManager.addLocation(location);
	}

	public Location addLocation(PlainAddress plainAddress) {
		return geoManager.addLocation(plainAddress);
	}

	public Location removeLocation(Location location) {
		return geoManager.removeLocation(location);
	}

	public Set<Location> queryWithRadius(QueryRadiusRequest queryRequest) {
		return geoManager.queryWithRadius(queryRequest);
	}

	public Set<Location> queryWithAddressAndRadius(QueryAddressRadiusRequest queryRequest) {
		return geoManager.queryWithAddressAndRadius(queryRequest);
	}

	public Set<Location> queryWithBoundaries(QueryBoundariesRequest queryRequest) {
		return geoManager.queryWithBoundaries(queryRequest);
	}

}
