package com.ucd.geoservices.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.service.LocationService;
import com.ucd.geoservices.transformer.DubLinkedTransformer;

@Path("/dataloader")
@Component
@Rest(isSingleton = true)
public class DataLoaderRest {

	@Autowired
	private LocationService locationService;

	@Autowired
	private DubLinkedTransformer dubLinkedTransformer;

	@POST
	@Path("dublinked")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response anotherTest(final String csvData) {
		List<Location> parkings = dubLinkedTransformer.transformFromCSVData(csvData);
		parkings.forEach(location -> locationService.addLocation(location));
		return Response.ok("Data Loaded : " + parkings.size()).build();
	}
}
