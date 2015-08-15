package com.ucd.geoservices.model;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.fixtures.LocationFixture;

public class LocationTest {

	@Test
	public void testEmptyConstructor() {
		Location location = new Location();

		assertThat(location.getName(), is(nullValue()));
		assertThat(location.getCoordinates(), is(nullValue()));
		assertThat(location.getId(), is(nullValue()));
		assertThat(location.getNumOfLocations(), is(nullValue()));
		assertThat(location.getMetaData().get(LocationMetaData.NAME.toString()), is(nullValue()));
		assertThat(location.getMetaData().get(LocationMetaData.TOTAL_LOCATIONS_HERE.toString()), is("1"));
	}

	@Test
	public void testFullConstructor() {
		Location location = LocationFixture.standard();

		assertThat(location.getCoordinates().getLatitude(), is(new Double(66)));
		assertThat(location.getCoordinates().getLongitude(), is(new Double(33)));
		assertThat(location.getName(), is("name"));
		assertThat(location.getId(), is("id"));
		assertThat(location.getNumOfLocations(), is("1"));

	}

	@Test
	public void testMetadata() {
		Location location = LocationFixture.standard2();

		assertThat(location.getMetaData().get(LocationMetaData.NAME.toString()), is(location.getName()));
		assertThat(location.getMetaData().get(LocationMetaData.TOTAL_LOCATIONS_HERE.toString()), is("2"));

	}

	@Test
	public void testJson() {
		Location expectedLocation = LocationFixture.standard();

		Location actualLocation = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(expectedLocation), Location.class);

		assertThat(expectedLocation, is(actualLocation));

	}
}
