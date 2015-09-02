package com.ucd.geoservices.rest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.QueryRadiusRequest;

public class LocationAsynchtest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Coordinates centralCoordinates = new Coordinates(53.324097, -6.22962);
		QueryRadiusRequest request = new QueryRadiusRequest(centralCoordinates, 100000);

		
		LongAdder succesful = new LongAdder();
		LongAdder errors = new LongAdder();


		//sync(request, succesful, errors);

		async(request, succesful, errors);

	}
	
	private static void sync(QueryRadiusRequest request, LongAdder succesful, LongAdder errors) {
		
		IntStream.range(0, 1000).parallel().forEach((index) -> {
			try{
			Response response = ClientBuilder.newClient().target("https://parkabled.herokuapp.com").path("/parkabled/resources/locations-asynch/query/radius")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
			
			System.out.println(response);
			succesful.increment();
			System.out.println("succesful : " + succesful.intValue() + " errors : " + errors.intValue() );
			}catch(Exception e){
				errors.increment();
				System.out.println("succesful : " + succesful.intValue() + " errors : " + errors.intValue() );
			}
		});
		
	}
	

	private static void async(QueryRadiusRequest request, LongAdder succesful, LongAdder errors) {
		IntStream.range(0, 1000).parallel().forEach((index) -> {
			final Future<Response> responseFuture = ClientBuilder.newClient().target("https://parkabled.herokuapp.com").path("/parkabled/resources/locations-asynch/query/radius")
					.request(MediaType.APPLICATION_JSON_TYPE).async()
					.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), new InvocationCallback<Response>() {
						@Override
						public void completed(Response response) {
							System.out.println("Response status code " + response.getStatus() + " received.");
							
							succesful.increment();
							
							System.out.println("succesful : " + succesful.intValue() + " errors : " + errors.intValue() );
						}

						@Override
						public void failed(Throwable throwable) {
							System.out.println("Invocation failed.");
							throwable.printStackTrace();
							errors.increment();
							
							System.out.println("succesful : " + succesful.intValue() + " errors : " + errors.intValue() );

						}
					});

		});
	}

}
