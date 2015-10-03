package com.ucd.geoservices.data;

import java.util.List;
import java.util.Optional;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationAction;
import com.ucd.geoservices.model.User;

public class BackendlessDataManager implements DataManager {

	public BackendlessDataManager() {
		String backendlessAppId = Optional.ofNullable(System.getenv("backendless-application-id")).orElse(
				System.getProperty("backendless-application-id"));
		String backendlessSecretId = Optional.ofNullable(System.getenv("backendless-secret-id")).orElse(System.getProperty("backendless-secret-id"));
		Backendless.initApp(backendlessAppId, backendlessSecretId, "v1");

	}

	@Override
	public LocationAction addLocationAction(User user, Location savedLocation, ACTION action) {
		LocationAction loctionAction = new LocationAction(savedLocation.getId(), user.getUsername(), action, System.currentTimeMillis());
		return Backendless.Persistence.save(loctionAction);
	}

	@Override
	public List<LocationAction> getLocationActions(Location location) {
		String whereClause = "locationId = '" + location.getId() + "'";
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		dataQuery.setWhereClause(whereClause);
		BackendlessCollection<LocationAction> result = Backendless.Persistence.of(LocationAction.class).find(dataQuery);
		return result.getData();
	}

	@Override
	public void removeLocationAction(LocationAction locationAction) {
		Backendless.Persistence.of(LocationAction.class).remove(locationAction);
	}

}
