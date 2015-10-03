package com.ucd.geoservices.data;

import java.util.List;

import com.ucd.geoservices.model.ACTION;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationAction;
import com.ucd.geoservices.model.User;

public interface DataManager {

	LocationAction addLocationAction(User user, Location savedLocation, ACTION action);

	void removeLocationAction(LocationAction locationAction);

	List<LocationAction> getLocationActions(Location location);

}
