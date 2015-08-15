package com.ucd.geoservices.geo;

import java.util.Set;

import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;

public interface GeoManager {

	public Location addLocation(Location location);

	public Location addLocation(PlainAddress plainAddress);

	public Location removeLocation(Location location);

	public Set<Location> queryWithRadius(QueryRadiusRequest queryRequest);

	public Set<Location> queryWithAddressAndRadius(QueryAddressRadiusRequest queryRequest);

	public Set<Location> queryWithBoundaries(QueryBoundariesRequest queryRequest);

}
