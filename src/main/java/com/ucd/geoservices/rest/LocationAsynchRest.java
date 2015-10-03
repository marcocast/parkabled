package com.ucd.geoservices.rest;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.PlainAddress;
import com.ucd.geoservices.model.QueryAddressRadiusRequest;
import com.ucd.geoservices.model.QueryBoundariesRequest;
import com.ucd.geoservices.model.QueryRadiusRequest;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.service.LocationService;
import com.ucd.geoservices.service.UserService;

@Path("/locations-asynch")
@Component
@Rest(isSingleton = true)
public class LocationAsynchRest {

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

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
	public void queryWithRadius(@Suspended final AsyncResponse asyncResponse, final String queryRequestJson) {
		Function<String, Response> locationResponseFunction = (queryRequestString) -> {
			QueryRadiusRequest queryRequest = JacksonUtil.convertFromJson(queryRequestString, QueryRadiusRequest.class);
			return Response.ok(JacksonUtil.serializeToJson(locationService.queryWithRadius(queryRequest))).build();
		};

		runAsynch(asyncResponse, queryRequestJson, locationResponseFunction);

	}

	@POST
	@Path("query/boundaries")
	@Consumes("application/json")
	@Produces("application/json")
	public void queryWithBoundaries(@Suspended final AsyncResponse asyncResponse, final String queryRequestJson) {

		Function<String, Response> locationResponseFunction = (queryRequestString) -> {
			QueryBoundariesRequest queryRequest = JacksonUtil.convertFromJson(queryRequestString, QueryBoundariesRequest.class);
			return Response.ok(JacksonUtil.serializeToJson(locationService.queryWithBoundaries(queryRequest))).build();
		};

		runAsynch(asyncResponse, queryRequestJson, locationResponseFunction);

	}

	@POST
	@Path("query/address")
	@Consumes("application/json")
	@Produces("application/json")
	public void queryWithAddress(@Suspended final AsyncResponse asyncResponse, final String queryAddressRadiusRequest) {

		Function<String, Response> locationResponseFunction = (queryRequestString) -> {
			QueryAddressRadiusRequest queryRequest = JacksonUtil.convertFromJson(queryRequestString, QueryAddressRadiusRequest.class);
			return Response.ok(JacksonUtil.serializeToJson(locationService.queryWithAddressAndRadius(queryRequest))).build();
		};

		runAsynch(asyncResponse, queryAddressRadiusRequest, locationResponseFunction);

	}

	@POST
	@Path("add/coordinates")
	@Consumes("application/json")
	@Produces("application/json")
	public void add(@Context HttpServletRequest request, @Suspended final AsyncResponse asyncResponse, final String locationJson) {
		User user = userService.getUser(request);
		Function<String, Response> locationResponseFunction = (queryRequestString) -> {
			Location location = JacksonUtil.convertFromJson(queryRequestString, Location.class);
			return Response.ok(JacksonUtil.serializeToJson(locationService.addLocation(user, location))).build();
		};

		runAsynch(asyncResponse, locationJson, locationResponseFunction);

	}

	@POST
	@Path("add/address")
	@Consumes("application/json")
	@Produces("application/json")
	public void addwithAddress(@Context HttpServletRequest request, @Suspended final AsyncResponse asyncResponse, final String addressJson) {
		User user = userService.getUser(request);
		Function<String, Response> locationResponseFunction = (queryRequestString) -> {
			PlainAddress plainAddress = JacksonUtil.convertFromJson(queryRequestString, PlainAddress.class);
			return Response.ok(JacksonUtil.serializeToJson(locationService.addLocation(user, plainAddress))).build();
		};

		runAsynch(asyncResponse, addressJson, locationResponseFunction);

	}

	@POST
	@Path("remove")
	@Consumes("application/json")
	@Produces("application/json")
	public void remove(@Context HttpServletRequest request, @Suspended final AsyncResponse asyncResponse, final String locationJson) {
		User user = userService.getUser(request);
		Function<String, Response> locationResponseFunction = (queryRequestString) -> {
			Location location = JacksonUtil.convertFromJson(queryRequestString, Location.class);
			return Response.ok(JacksonUtil.serializeToJson(locationService.removeLocation(user, location))).build();
		};

		runAsynch(asyncResponse, locationJson, locationResponseFunction);

	}

	private void runAsynch(final AsyncResponse asyncResponse, final String queryRequestJson, Function<String, Response> locationResponseFunction) {

		asyncResponse.setTimeoutHandler(new TimeoutHandler() {

			@Override
			public void handleTimeout(AsyncResponse asyncResponse) {
				asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out.").build());
			}
		});
		asyncResponse.setTimeout(10, TimeUnit.SECONDS);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Response result = locationResponseFunction.apply(queryRequestJson);
				asyncResponse.resume(result);
			}

		}).start();
	}

}
