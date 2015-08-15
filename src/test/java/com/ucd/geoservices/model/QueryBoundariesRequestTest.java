package com.ucd.geoservices.model;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.rest.JacksonUtil;

public class QueryBoundariesRequestTest {

	@Test
	public void testEmptyConstructor() {
		QueryBoundariesRequest queryBoundariesRequest = new QueryBoundariesRequest();

		assertThat(queryBoundariesRequest.getTopLeftCoordinates(), is(nullValue()));
		assertThat(queryBoundariesRequest.getBottomRightCoordinates(), is(nullValue()));

	}

	@Test
	public void testFullConstructor() {
		QueryBoundariesRequest queryBoundariesRequest = new QueryBoundariesRequest(new Coordinates(33, 66), new Coordinates(44, 88));

		assertThat(queryBoundariesRequest.getTopLeftCoordinates(), is(new Coordinates(33, 66)));
		assertThat(queryBoundariesRequest.getBottomRightCoordinates(), is(new Coordinates(44, 88)));

	}

	@Test
	public void testJson() {
		QueryBoundariesRequest expectedQueryBoundariesRequest = new QueryBoundariesRequest(new Coordinates(33, 66), new Coordinates(44, 88));

		QueryBoundariesRequest actualQueryBoundariesRequest = JacksonUtil.convertFromJson(
				JacksonUtil.serializeToJson(expectedQueryBoundariesRequest), QueryBoundariesRequest.class);

		assertThat(expectedQueryBoundariesRequest, is(actualQueryBoundariesRequest));

	}
}
