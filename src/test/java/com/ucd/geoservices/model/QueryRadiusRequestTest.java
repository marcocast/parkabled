package com.ucd.geoservices.model;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.rest.JacksonUtil;

public class QueryRadiusRequestTest {

	@Test
	public void testEmptyConstructor() {
		QueryRadiusRequest queryRadiusRequest = new QueryRadiusRequest();

		assertThat(queryRadiusRequest.getCentralCoordinates(), is(nullValue()));
		assertThat(queryRadiusRequest.getRadius(), is(new Double(0)));

	}

	@Test
	public void testFullConstructor() {
		QueryRadiusRequest queryRadiusRequest = new QueryRadiusRequest(new Coordinates(33, 66), 2);

		assertThat(queryRadiusRequest.getCentralCoordinates(), is(new Coordinates(33, 66)));
		assertThat(queryRadiusRequest.getRadius(), is(new Double(2)));

	}

	@Test
	public void testJson() {
		QueryRadiusRequest expectedQueryRadiusRequest = new QueryRadiusRequest(new Coordinates(33, 66), 2);

		QueryRadiusRequest actualQueryRadiusRequest = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(expectedQueryRadiusRequest),
				QueryRadiusRequest.class);

		assertThat(expectedQueryRadiusRequest, is(actualQueryRadiusRequest));

	}
}
