package com.ucd.geoservices.fixtures;

import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;

public class LocationFixture {

	public static Location standard() {
		return new Location("id", new Coordinates(33, 66), "name", "1");
	}

	public static Location standard2() {
		return new Location("id", new Coordinates(33, 66), "name", "2");
	}

	public static Location standardNoCoordinates() {
		return new Location("id", null, "name", "2");
	}

}
