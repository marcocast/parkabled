package com.ucd.geoservices.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.data.DataManager;
import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.User;

@Component
public class DataService {

	@Autowired
	private DataManager dataManager;

	public boolean entryExists(Location location, String username, ACTION action) {
		return dataManager.getLocationActions(location).stream().filter(locationAction -> locationAction.getUsername().equals(username))
				.anyMatch(locationAction -> locationAction.getAction() == action);
	}

	public void addLocationAction(User user, Location location, ACTION action) {
		dataManager.addLocationAction(user, location, action);
	}

	public void removeLocationActions(Location location) {
		dataManager.getLocationActions(location).stream().forEach(locationAction -> {
			dataManager.removeLocationAction(locationAction);
		});

	}

	public long totalRemovingVotes(Location location) {
		Map<ACTION, Long> totalByAction = totalEntries(location);
		long removedVotes = Optional.ofNullable(totalByAction.get(ACTION.REMOVED)).orElse(0l);
		long addedVotes = Optional.ofNullable(totalByAction.get(ACTION.ADDED)).orElse(0l);

		return removedVotes - addedVotes;
	}

	private Map<ACTION, Long> totalEntries(Location location) {
		return dataManager.getLocationActions(location).stream().map(locationAction -> locationAction.getAction())
				.collect(Collectors.groupingBy(o -> o, Collectors.counting()));

	}

}
