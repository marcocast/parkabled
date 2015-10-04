package com.ucd.geoservices.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.geo.GeoManager;
import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.transformer.LocationTransformer;

@Component
public class LocationService {

	@Autowired
	private GeoManager geoManager;

	@Autowired
	private DataService dataService;

	@Autowired
	private LocationTransformer locationTransformer;

	public Location addLocation(User user, Location location) {
		Location savedLocation = geoManager.addLocation(location);
		dataService.addLocationAction(user, savedLocation, ACTION.ADDED);
		return savedLocation;
	}

	public Location addLocation(User user, PlainAddress plainAddress) {
		return addLocation(user, locationTransformer.plainAddressToLocation(plainAddress));
	}

	public Location removeLocation(Location location) {
		return geoManager.removeLocation(location);
	}

	public Location updateLocation(Location location) {
		return geoManager.updateLocation(location);
	}

	public Set<Location> queryWithRadius(QueryRadiusRequest queryRequest) {
		return geoManager.queryWithRadius(queryRequest);
	}

	public Set<Location> queryWithAddressAndRadius(QueryAddressRadiusRequest queryRequest) {
		return queryWithRadius(locationTransformer.addressToLocationRadiusRequest(queryRequest));
	}

	public Set<Location> queryWithBoundaries(QueryBoundariesRequest queryRequest) {
		return geoManager.queryWithBoundaries(queryRequest);
	}

}
