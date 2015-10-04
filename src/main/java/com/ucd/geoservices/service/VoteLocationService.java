package com.ucd.geoservices.service;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.ErrorMessage;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationAction;
import com.ucd.geoservices.model.LocationMetaData;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.transformer.LocationTransformer;

@Component
public class VoteLocationService {

	@Autowired
	private LocationService locationService;

	@Autowired
	private DataService dataService;

	@Autowired
	private LocationTransformer locationTransformer;

	public Location voteToAddLocation(User user, Location location) {
		List<LocationAction> allLocationActions = dataService.getAllLocationActions(location);
		checkUserAlreadyVoted(allLocationActions, user, ACTION.ADDED);

		long totalRemovingVotes = dataService.totalRemovingVotes(allLocationActions);

		if (totalRemovingVotes < 1) {
			updateRemovingVotes(location, --totalRemovingVotes);
		}
		location = locationService.updateLocation(location);
		dataService.addLocationAction(user, location, ACTION.ADDED);
		return location;
	}

	public Location voteToRemoveLocation(User user, Location location) {
		List<LocationAction> allLocationActions = dataService.getAllLocationActions(location);
		checkUserAlreadyVoted(allLocationActions, user, ACTION.REMOVED);
		long totalRemovingVotes = dataService.totalRemovingVotes(allLocationActions);

		if (totalRemovingVotes >= 2) {
			locationService.removeLocation(location);
			dataService.removeLocationActions(allLocationActions);
		} else {
			updateRemovingVotes(location, ++totalRemovingVotes);
			location = locationService.updateLocation(location);
			dataService.addLocationAction(user, location, ACTION.REMOVED);
		}

		return location;

	}

	private void checkUserAlreadyVoted(List<LocationAction> allLocationActions, User user, ACTION action) {
		if (dataService.entryExists(allLocationActions, user.getUsername(), action)) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
					.entity(new ErrorMessage("User " + user.getUsername() + " already removed this location")).type(MediaType.APPLICATION_JSON)
					.build());
		}

	}

	private void updateRemovingVotes(Location location, long newCredibility) {
		location.getMetadata().put(LocationMetaData.REMOVAL_VOTES.toString(), String.valueOf(newCredibility));
	}

}
