package com.ucd.geoservices.transformer;

import org.springframework.stereotype.Component;

import com.backendless.geo.GeoPoint;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;

@Component
public class LocationTransformer {

	public Location geoPointToLocation(GeoPoint geoPoint) {
		return new Location(geoPoint.getObjectId(), new Coordinates(geoPoint.getLongitude(), geoPoint.getLatitude()), geoPoint.getMetadata());
	}

	public Location plainAddressToLocation(PlainAddress plainAddress, Coordinates coordinates) {
		return new Location(null, coordinates, plainAddress.getMetadata());
	}

}
