package com.ucd.geoservices.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;
import com.ucd.geoservices.fixtures.LocationFixture;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.service.LocationService;
import com.ucd.geoservices.service.UserService;
import com.ucd.geoservices.transformer.DubLinkedTransformer;

public class DataLoaderRestTest {

	@InjectMocks
	private DataLoaderRest dataLoaderRest;

	@Mock
	private LocationService locationService;

	@Mock
	private UserService userService;

	@Mock
	private DubLinkedTransformer dubLinkedTransformer;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(userService.getUser(any(HttpServletRequest.class))).thenReturn(new User("email", "password", "username", "status"));
	}

	@Test
	public void testLoad() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		String csvData = "Alborough Parade,D1,1,No 4/6,9910";

		List<Location> locations = Lists.newArrayList(LocationFixture.standard());
		when(dubLinkedTransformer.transformFromCSVData("Ireland", "Dublin", csvData)).thenReturn(locations);

		Response response = dataLoaderRest.load(request, csvData);

		assertThat(response.getEntity().toString(), is("Data Loaded : 1"));

	}

}
