package com.ucd.geoservices.transformer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backendless.geo.GeoPoint;
import com.ucd.geoservices.geo.GeocoderService;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationMetaData;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;

@Component
public class LocationTransformer {

	@Autowired
	private GeocoderService geocoderService;

	@Value("${totalRemovalVotesToDelete}")
	private String totalRemovalVotesToDelete;

	public Location geoPointToLocation(GeoPoint geoPoint) {
		Map<String, String> metadata = geoPoint.getMetadata();
		metadata.putIfAbsent(LocationMetaData.REMOVAL_VOTES.toString(), "0");
		metadata.putIfAbsent(LocationMetaData.TOTAL_REMOVAL_VOTES_TO_DELETE.toString(), totalRemovalVotesToDelete);
		return new Location(geoPoint.getObjectId(), new Coordinates(geoPoint.getLongitude(), geoPoint.getLatitude()), metadata);
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
