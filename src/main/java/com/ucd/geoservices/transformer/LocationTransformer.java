package com.ucd.geoservices.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backendless.geo.GeoPoint;
import com.ucd.geoservices.geo.GeocoderService;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;

@Component
public class LocationTransformer {

	@Autowired
	private GeocoderService geocoderService;

	public Location geoPointToLocation(GeoPoint geoPoint) {
		return new Location(geoPoint.getObjectId(), new Coordinates(geoPoint.getLongitude(), geoPoint.getLatitude()), geoPoint.getMetadata());
	}

	public Location plainAddressToLocation(PlainAddress plainAddress, Coordinates coordinates) {
		return new Location(null, coordinates, plainAddress.getMetadata());
	}

	public Location plainAddressToLocation(PlainAddress plainAddress) {
		try {
			return geocoderService.getLongLat(plainAddress.getFullAddressString()).map(pair -> {
				return plainAddressToLocation(plainAddress, new Coordinates(pair.getV1(), pair.getV2()));
			}).get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public QueryRadiusRequest addressToLocationRadiusRequest(QueryAddressRadiusRequest queryRequest) {
		return new QueryRadiusRequest(plainAddressToLocation(queryRequest.getPlainAddress()).getCoordinates(), queryRequest.getRadius());

	}

}
