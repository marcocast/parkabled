package com.ucd.geoservices.fixtures;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ucd.geoservices.model.LocationMetaData;
import com.ucd.geoservices.model.PlainAddress;

public class PlainAddressFixture {

	public static PlainAddress wexfordStreet33() {
		Map<String, String> metadata = Maps.newHashMap();
		metadata.put(LocationMetaData.NAME.toString(), "33");
		metadata.put(LocationMetaData.NUM_OF_LOCATIONS.toString(), "2");
		return new PlainAddress("Ireland", "Dublin", "wexford", "33", metadata);
	}

}
