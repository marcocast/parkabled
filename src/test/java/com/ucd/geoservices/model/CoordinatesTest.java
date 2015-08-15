package com.ucd.geoservices.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.rest.JacksonUtil;

public class CoordinatesTest {

	@Test
	public void testEmptyConstructor() {
		Coordinates coordinates = new Coordinates();

		assertThat(coordinates.getLatitude(), is(new Double(0)));
		assertThat(coordinates.getLongitude(), is(new Double(0)));

	}

	@Test
	public void testFullConstructor() {
		Coordinates coordinates = new Coordinates(33, 66);

		assertThat(coordinates.getLatitude(), is(new Double(66)));
		assertThat(coordinates.getLongitude(), is(new Double(33)));

	}

	@Test
	public void testJson() {
		Coordinates expectedCoordinates = new Coordinates(33, 66);

		Coordinates actualCoordinates = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(expectedCoordinates), Coordinates.class);

		assertThat(expectedCoordinates, is(actualCoordinates));

	}
}
