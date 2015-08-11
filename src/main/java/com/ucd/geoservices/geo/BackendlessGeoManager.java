package com.ucd.geoservices.geo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.backendless.geo.Units;
import com.google.common.collect.Lists;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;
import com.ucd.geoservices.transformer.LocationTransformer;

import jersey.repackaged.com.google.common.collect.Sets;


public class BackendlessGeoManager implements GeoManager {

	private final LocationTransformer geoPointTransformer;
	private final GeocoderService geocoderService;

	public BackendlessGeoManager(LocationTransformer geoPointTransformer, GeocoderService geocoderService) {
		String backendlessAppId = Optional.ofNullable(System.getenv("backendless-application-id"))
				.orElse(System.getProperty("backendless-application-id"));
		String backendlessSecretId = Optional.ofNullable(System.getenv("backendless-secret-id"))
				.orElse(System.getProperty("backendless-secret-id"));
		Backendless.initApp(backendlessAppId, backendlessSecretId, "v1");
		this.geoPointTransformer = geoPointTransformer;
		this.geocoderService = geocoderService;

	}

	@Override
	public Location addLocation(Location location) {
		QueryRadiusRequest queryRequest = new QueryRadiusRequest(location.getCoordinates(), 5);
		List<GeoPoint> existingNearbyPoints = getGeoPointsWithRadius(queryRequest.getCentralCoordinates().getLatitude(),
				queryRequest.getCentralCoordinates().getLongitude(), queryRequest.getRadius());
		if (existingNearbyPoints.isEmpty()) {
			GeoPoint savedGeoPoint = Backendless.Geo.savePoint(location.getCoordinates().getLatitude(),
					location.getCoordinates().getLongitude(), location.getMetaData());
			return location.withId(savedGeoPoint.getObjectId());
		} else {

			throw new RuntimeException("");
		}

	}

	@Override
	public Location addLocation(PlainAddress plainAddress) {
		return geocoderService.getLongLat(plainAddress.getFullAddressString()).map(pair -> {
			Location location = geoPointTransformer.plainAddressToLocation(plainAddress,
					new Coordinates(pair.getV1(), pair.getV2()));

			return addLocation(location);
		}).get();

	}

	@Override
	public Location removeLocation(Location location) {
		GeoPoint todelete = new GeoPoint();
		todelete.setObjectId(location.getId());
		Backendless.Geo.removePoint(todelete);
		return location;
	}

	@Override
	public Set<Location> queryWithRadius(QueryRadiusRequest queryRequest) {

		return getGeoPointsWithRadius(queryRequest.getCentralCoordinates().getLongitude(),
				queryRequest.getCentralCoordinates().getLatitude(), queryRequest.getRadius()).stream()
						.map(geoPoint -> geoPointTransformer.geoPointToLocation(geoPoint)).collect(Collectors.toSet());
	}

	@Override
	public Set<Location> queryWithAddressAndRadius(QueryAddressRadiusRequest queryRequest) {

		return geocoderService.getLongLat(queryRequest.getPlainAddress().getFullAddressString()).map(pair -> {
			return getGeoPointsWithRadius(pair.getV1(), pair.getV2(), queryRequest.getRadius()).stream()
					.map(geoPoint -> geoPointTransformer.geoPointToLocation(geoPoint)).collect(Collectors.toSet());

		}).orElse(Sets.newHashSet());

	}

	@Override
	public Set<Location> queryWithBoundaries(QueryBoundariesRequest queryRequest) {

		BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
		GeoPoint topLeft = new GeoPoint(queryRequest.getTopLeftCoordinates().getLatitude(),
				queryRequest.getTopLeftCoordinates().getLongitude());
		GeoPoint bottomright = new GeoPoint(queryRequest.getBottomRightCoordinates().getLatitude(),
				queryRequest.getBottomRightCoordinates().getLongitude());
		geoQuery.setSearchRectangle(topLeft, bottomright);
		BackendlessCollection<GeoPoint> points = Backendless.Geo.getPoints(geoQuery);

		List<GeoPoint> geoPoints = Lists.newArrayList();
		while (points.getCurrentPage().size() > 0) {
			geoPoints.addAll(points.getData());
			points = points.nextPage();
		}
		return geoPoints.stream().map(geoPoint -> geoPointTransformer.geoPointToLocation(geoPoint))
				.collect(Collectors.toSet());
	}

	private List<GeoPoint> getGeoPointsWithRadius(double longitude, double latitude, double radius) {
		List<GeoPoint> geoPoints = Lists.newArrayList();
		BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
		geoQuery.setLatitude(latitude);
		geoQuery.setLongitude(longitude);
		geoQuery.setRadius(radius);
		geoQuery.setUnits(Units.METERS);
		BackendlessCollection<GeoPoint> points = Backendless.Geo.getPoints(geoQuery);

		while (points.getCurrentPage().size() > 0) {
			geoPoints.addAll(points.getData());
			points = points.nextPage();
		}
		return geoPoints;
	}

}
