package com.ucd.geoservices.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.service.LocationService;
import com.ucd.geoservices.service.UserService;
import com.ucd.geoservices.transformer.DubLinkedTransformer;

@Path("/dataloader")
@Component
@Rest(isSingleton = true)
public class DataLoaderRest {

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

	@Autowired
	private DubLinkedTransformer dubLinkedTransformer;

	@POST
	@Path("dublinked")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response load(@Context HttpServletRequest request, final String csvData) {
		User user = userService.getUser(request);
		List<Location> parkings = dubLinkedTransformer.transformFromCSVData("Ireland", "Dublin", csvData);

		parkings.forEach(location -> locationService.addLocation(user, location));
		return Response.ok("Data Loaded : " + parkings.size()).build();
	}

	@POST
	@Path("csv/{country}/{city}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response loadCSV(@Context HttpServletRequest request, @PathParam("country") String country, @PathParam("city") String city,
			final String csvData) {
		User user = userService.getUser(request);
		List<Location> parkings = dubLinkedTransformer.transformFromCSVData(country, city, csvData);

		parkings.forEach(location -> locationService.addLocation(user, location));
		return Response.ok("Data Loaded : " + parkings.size()).build();
	}

}
