package com.ucd.geoservices.model;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.fixtures.PlainAddressFixture;

public class QueryAddressRadiusRequestTest {

	@Test
	public void testEmptyConstructor() {
		QueryAddressRadiusRequest queryAddressRadiusRequest = new QueryAddressRadiusRequest();

		assertThat(queryAddressRadiusRequest.getPlainAddress(), is(nullValue()));
		assertThat(queryAddressRadiusRequest.getRadius(), is(new Double(0)));

	}

	@Test
	public void testFullConstructor() {
		QueryAddressRadiusRequest queryAddressRadiusRequest = new QueryAddressRadiusRequest(PlainAddressFixture.wexfordStreet33(), 2);

		assertThat(queryAddressRadiusRequest.getPlainAddress(), is(PlainAddressFixture.wexfordStreet33()));
		assertThat(queryAddressRadiusRequest.getRadius(), is(new Double(2)));

	}

	@Test
	public void testJson() {
		QueryAddressRadiusRequest expectedQueryAddressRadiusRequest = new QueryAddressRadiusRequest(PlainAddressFixture.wexfordStreet33(), 2);

		QueryAddressRadiusRequest actualQueryAddressRadiusRequest = JacksonUtil.convertFromJson(
				JacksonUtil.serializeToJson(expectedQueryAddressRadiusRequest), QueryAddressRadiusRequest.class);

		assertThat(expectedQueryAddressRadiusRequest, is(actualQueryAddressRadiusRequest));

	}
}
