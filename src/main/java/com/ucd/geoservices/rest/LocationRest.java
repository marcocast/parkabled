package com.ucd.geoservices.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;
import com.ucd.geoservices.service.LocationService;

@Path("/locations")
@Component
@Rest(isSingleton = true)
public class LocationRest {

	@Autowired
	private LocationService locationService;

	@GET
	@Produces("text/plain")
	@Path("/info")
	public Response info() {
		return Response.ok("Some info here").build();
	}

	@POST
	@Path("query/radius")
	@Consumes("application/json")
	@Produces("application/json")
	public Response queryWithRadius(final String queryRequestJson) {
		QueryRadiusRequest queryRequest = JacksonUtil.convertFromJson(queryRequestJson, QueryRadiusRequest.class);

		return Response.ok(JacksonUtil.serializeToJson(locationService.queryWithRadius(queryRequest))).build();
	}

	@POST
	@Path("query/boundaries")
	@Consumes("application/json")
	@Produces("application/json")
	public Response queryWithBoundaries(final String queryRequestJson) {
		QueryBoundariesRequest queryRequest = JacksonUtil.convertFromJson(queryRequestJson,
				QueryBoundariesRequest.class);

		return Response.ok(JacksonUtil.serializeToJson(locationService.queryWithBoundaries(queryRequest))).build();
	}

	@POST
	@Path("query/address")
	@Consumes("application/json")
	@Produces("application/json")
	public Response queryWithAddress(final String queryAddressRadiusRequest) {
		QueryAddressRadiusRequest queryRequest = JacksonUtil.convertFromJson(queryAddressRadiusRequest,
				QueryAddressRadiusRequest.class);

		return Response.ok(JacksonUtil.serializeToJson(locationService.queryWithAddressAndRadius(queryRequest)))
				.build();
	}

	@POST
	@Path("add/coordinates")
	@Consumes("application/json")
	@Produces("application/json")
	public Response add(final String locationJson) {
		Location location = JacksonUtil.convertFromJson(locationJson, Location.class);

		return Response.ok(JacksonUtil.serializeToJson(locationService.addLocation(location))).build();
	}

	@POST
	@Path("add/address")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addwithAddress(final String addressJson) {
		PlainAddress plainAddress = JacksonUtil.convertFromJson(addressJson, PlainAddress.class);
		return Response.ok(JacksonUtil.serializeToJson(locationService.addLocation(plainAddress))).build();
	}

	@POST
	@Path("remove")
	@Consumes("application/json")
	@Produces("application/json")
	public Response remove(final String locationJson) {
		Location location = JacksonUtil.convertFromJson(locationJson, Location.class);

		return Response.ok(JacksonUtil.serializeToJson(locationService.removeLocation(location))).build();
	}

}
