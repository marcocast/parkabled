package com.ucd.geoservices.model;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.fixtures.PlainAddressFixture;

public class PlainAddressTest {

	@Test
	public void testEmptyConstructor() {
		PlainAddress plainAddress = new PlainAddress();

		assertThat(plainAddress.getCountry(), is(nullValue()));
		assertThat(plainAddress.getCity(), is(nullValue()));
		assertThat(plainAddress.getStreet(), is(nullValue()));
		assertThat(plainAddress.getNumber(), is(nullValue()));
		assertThat(plainAddress.getNumOfLocations(), is(nullValue()));

	}

	@Test
	public void testFullConstructor() {
		PlainAddress plainAddress = PlainAddressFixture.wexfordStreet33();

		assertThat(plainAddress.getFullAddressString(), is("Ireland Dublin wexford 33"));

	}

	@Test
	public void testJson() {
		PlainAddress expectedPlainAddress = PlainAddressFixture.wexfordStreet33();

		PlainAddress actualPlainAddress = JacksonUtil.convertFromJson(JacksonUtil.serializeToJson(expectedPlainAddress),
				PlainAddress.class);

		assertThat(expectedPlainAddress, is(actualPlainAddress));

	}
}
