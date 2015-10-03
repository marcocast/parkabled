package com.ucd.geoservices.service;

import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.geo.GeoManager;
import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.ErrorMessage;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationMetaData;
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

	public Location removeLocation(User user, Location location) {
		if (dataService.entryExists(location, user.getUsername(), ACTION.REMOVED)) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
					.entity(new ErrorMessage("User " + user.getUsername() + " already removed this location")).type(MediaType.APPLICATION_JSON)
					.build());
		}

		long totalRemovingVotes = dataService.totalRemovingVotes(location);

		if (totalRemovingVotes >= 2) {
			geoManager.removeLocation(location);
			dataService.removeLocationActions(location);
		} else {
			increaseRemovingVotes(location, ++totalRemovingVotes);
			geoManager.updateLocation(location);
			dataService.addLocationAction(user, location, ACTION.REMOVED);
		}

		return location;

	}

	private void increaseRemovingVotes(Location location, long newCredibility) {
		location.getMetadata().put(LocationMetaData.REMOVAL_VOTES.toString(), String.valueOf(newCredibility));
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
