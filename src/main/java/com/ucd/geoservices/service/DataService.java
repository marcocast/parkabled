package com.ucd.geoservices.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.data.DataManager;
import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationAction;
import com.ucd.geoservices.model.User;

@Component
public class DataService {

	@Autowired
	private DataManager dataManager;

	public List<LocationAction> getAllLocationActions(Location location) {
		return dataManager.getLocationActions(location);
	}

	public boolean entryExists(List<LocationAction> locationActions, String username, ACTION action) {
		return locationActions.stream().filter(locationAction -> locationAction.getUsername().equals(username))
				.anyMatch(locationAction -> locationAction.getAction() == action);
	}

	public void addLocationAction(User user, Location location, ACTION action) {
		dataManager.addLocationAction(user, location, action);
	}

	public void removeLocationActions(List<LocationAction> locationActions) {
		locationActions.stream().forEach(locationAction -> {
			dataManager.removeLocationAction(locationAction);
		});

	}

	public long totalRemovingVotes(List<LocationAction> locationActions) {
		Map<ACTION, Long> totalByAction = totalEntries(locationActions);
		long removedVotes = Optional.ofNullable(totalByAction.get(ACTION.REMOVED)).orElse(0l);
		long addedVotes = Optional.ofNullable(totalByAction.get(ACTION.ADDED)).orElse(0l);

		return removedVotes - addedVotes;
	}

	private Map<ACTION, Long> totalEntries(List<LocationAction> locationActions) {
		return locationActions.stream().map(locationAction -> locationAction.getAction())
				.collect(Collectors.groupingBy(o -> o, Collectors.counting()));

	}

}
