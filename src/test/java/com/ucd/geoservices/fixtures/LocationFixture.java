package com.ucd.geoservices.fixtures;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationMetaData;

public class LocationFixture {

	public static Location standard() {
		Map<String, String> metadata = Maps.newHashMap();
		metadata.put(LocationMetaData.NAME.toString(), "name");
		metadata.put(LocationMetaData.NUM_OF_LOCATIONS.toString(), "1");
		return new Location("id", new Coordinates(33, 66), metadata);
	}

	public static Location standard2() {
		Map<String, String> metadata = Maps.newHashMap();
		metadata.put(LocationMetaData.NAME.toString(), "name");
		metadata.put(LocationMetaData.NUM_OF_LOCATIONS.toString(), "2");
		return new Location("id", new Coordinates(33, 66), metadata);
	}

	public static Location standardNoCoordinates() {
		Map<String, String> metadata = Maps.newHashMap();
		metadata.put(LocationMetaData.NAME.toString(), "name");
		metadata.put(LocationMetaData.NUM_OF_LOCATIONS.toString(), "2");
		return new Location("id", null, metadata);
	}

}
