package com.ucd.geoservices.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.service.UserService;
import com.ucd.geoservices.service.VoteLocationService;

@Path("/locations/vote")
@Component
@Rest(isSingleton = true)
public class VoteLocationRest {

	@Autowired
	private VoteLocationService voteLocationService;

	@Autowired
	private UserService userService;

	@POST
	@Path("add")
	@Consumes("application/json")
	@Produces("application/json")
	public Response add(@Context HttpServletRequest request, final String locationJson) {
		User user = userService.getUser(request);
		Location location = JacksonUtil.convertFromJson(locationJson, Location.class);
		return Response.ok(JacksonUtil.serializeToJson(voteLocationService.voteToAddLocation(user, location))).build();
	}

	@POST
	@Path("remove")
	@Consumes("application/json")
	@Produces("application/json")
	public Response remove(@Context HttpServletRequest request, final String locationJson) {
		User user = userService.getUser(request);
		Location location = JacksonUtil.convertFromJson(locationJson, Location.class);
		return Response.ok(JacksonUtil.serializeToJson(voteLocationService.voteToRemoveLocation(user, location))).build();
	}

}
