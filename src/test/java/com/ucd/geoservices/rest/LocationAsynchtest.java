package com.ucd.geoservices.rest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.QueryRadiusRequest;

public class LocationAsynchtest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Coordinates centralCoordinates = new Coordinates(53.324097, -6.22962);
		QueryRadiusRequest request = new QueryRadiusRequest(centralCoordinates, 100000);

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target("http://localhost:8080");


		final Future<Response> responseFuture = target.path("/parkabled/resources/locations-asynch/query/radius").request(MediaType.APPLICATION_JSON_TYPE).async()
				.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), new InvocationCallback<Response>() {
					@Override
					public void completed(Response response) {
						System.out.println("Response status code " + response.getStatus() + " received.");
						System.out.println("Response  " + response);
					}

					@Override
					public void failed(Throwable throwable) {
						System.out.println("Invocation failed.");
						throwable.printStackTrace();
					}
				});

	}

}
