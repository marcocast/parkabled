package com.ucd.geoservices.geo;

import java.util.Set;

import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;

public interface GeoManager {

	public Location addLocation(Location location);

	public Location removeLocation(Location location);

	public Location updateLocation(Location location);

	public Set<Location> queryWithRadius(QueryRadiusRequest queryRequest);

	public Set<Location> queryWithBoundaries(QueryBoundariesRequest queryRequest);

}
