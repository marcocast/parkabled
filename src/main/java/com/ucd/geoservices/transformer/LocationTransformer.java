package com.ucd.geoservices.transformer;

import org.springframework.stereotype.Component;

import com.backendless.geo.GeoPoint;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationMetaData;
import com.ucd.geoservices.model.PlainAddress;

@Component
public class LocationTransformer {

	public Location geoPointToLocation(GeoPoint geoPoint) {
		return new Location(geoPoint.getObjectId(), new Coordinates(geoPoint.getLongitude(), geoPoint.getLatitude()),
				geoPoint.getMetadata(LocationMetaData.NAME.toString()),
				geoPoint.getMetadata(LocationMetaData.TOTAL_LOCATIONS_HERE.toString()));

	}

	public Location plainAddressToLocation(PlainAddress plainAddress, Coordinates coordinates) {
		return new Location(null, coordinates, plainAddress.getFullAddressString(),
				plainAddress.getTotalLocationsHere());

	}

}
