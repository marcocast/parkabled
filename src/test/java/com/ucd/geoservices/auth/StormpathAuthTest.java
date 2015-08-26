package com.ucd.geoservices.auth;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.ucd.geoservices.fixtures.AccountFitures;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.transformer.UserTransformer;

public class StormpathAuthTest {

	private StormpathAuth stormpathAuth;

	private UserTransformer userTransformer;
	@Mock
	private StormpathProvider stormpathProvider;

	private Integer acessTokenExpirationInMinutes = 10;
	@Mock
	private Client client;
	@Mock
	private Application application;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userTransformer = new UserTransformer();
		this.stormpathAuth = new StormpathAuth(userTransformer, stormpathProvider, acessTokenExpirationInMinutes);
		Mockito.when(stormpathProvider.getClient()).thenReturn(client);
		Mockito.when(stormpathProvider.getApplication()).thenReturn(application);
	}

	@Test
	public void testcreate() {
		User user = new User("email", "password", "username", "status");

		Account account = AccountFitures.createEmptyAccount();

		Mockito.when(client.instantiate(Account.class)).thenReturn(account);

		Mockito.when(application.createAccount(account)).thenReturn(account);

		User actual = stormpathAuth.create(user);

		assertThat(actual.getEmail(), is(user.getEmail()));
		assertThat(actual.getUsername(), is(user.getUsername()));
	}
}
