package com.ucd.geoservices.auth;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiAuthenticationResult;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.http.HttpRequest;
import com.stormpath.sdk.oauth.AccessTokenResult;
import com.stormpath.sdk.oauth.OauthRequestAuthenticator;
import com.stormpath.sdk.oauth.TokenResponse;
import com.ucd.geoservices.fixtures.AccountFitures;
import com.ucd.geoservices.fixtures.ApiKeyListFixture;
import com.ucd.geoservices.fixtures.HttpServletRequestFixture;
import com.ucd.geoservices.fixtures.ResourceExceptionFixture;
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

	@Test(expected = WebApplicationException.class)
	public void testcreateException() {
		User user = new User("email", "password", "username", "status");
		Account account = AccountFitures.createEmptyAccount();

		Mockito.when(client.instantiate(Account.class)).thenReturn(account);

		Mockito.when(application.createAccount(account)).thenThrow(ResourceExceptionFixture.newResourceException(2, "message", "developerMessage"));
		stormpathAuth.create(user);
	}

	@Test
	public void testgetRefreshToken() {
		Account account = Mockito.mock(Account.class);

		AuthenticationResult authResult = Mockito.mock(AuthenticationResult.class);

		ApiKey apiKey = Mockito.mock(ApiKey.class);
		Mockito.when(account.createApiKey()).thenReturn(apiKey);
		Mockito.when(apiKey.getId()).thenReturn("ID");
		Mockito.when(apiKey.getSecret()).thenReturn("SECRET");

		Mockito.when(application.authenticateAccount(org.mockito.Matchers.any(AuthenticationRequest.class))).thenReturn(authResult);
		Mockito.when(authResult.getAccount()).thenReturn(account);

		String refreshToken = stormpathAuth.getRefreshToken("email", "password");
		String decodedToken = new String(Base64.decodeBase64(refreshToken));
		assertThat(decodedToken, is("ID:SECRET"));
	}

	@Test(expected = WebApplicationException.class)
	public void testgetRefreshTokenException() {

		Mockito.when(application.authenticateAccount(org.mockito.Matchers.any(AuthenticationRequest.class))).thenThrow(
				ResourceExceptionFixture.newResourceException(2, "message", "developerMessage"));

		stormpathAuth.getRefreshToken("email", "password");
	}

	@Test
	public void testsendPasswordResetEmail() {
		Account account = AccountFitures.createEmptyAccount();

		Mockito.when(application.sendPasswordResetEmail("email")).thenReturn(account);

		User actual = stormpathAuth.sendPasswordResetEmail("email");
		assertThat(actual.getEmail(), is(account.getEmail()));
		assertThat(actual.getUsername(), is(account.getUsername()));
	}

	@Test(expected = WebApplicationException.class)
	public void testsendPasswordResetEmailException() {
		Mockito.when(application.sendPasswordResetEmail("email")).thenThrow(
				ResourceExceptionFixture.newResourceException(2, "message", "developerMessage"));

		stormpathAuth.sendPasswordResetEmail("email");

	}

	@Test
	public void testgenerateAccessToken() {
		String apiKeyToken = "testapiKeyToken";

		OauthRequestAuthenticator oauthRequestAuthenticator = Mockito.mock(OauthRequestAuthenticator.class);

		com.stormpath.sdk.oauth.AccessTokenRequestAuthenticator accessTokenRequestAuthenticator = Mockito
				.mock(com.stormpath.sdk.oauth.AccessTokenRequestAuthenticator.class);

		AccessTokenResult resultToken = Mockito.mock(AccessTokenResult.class);

		Mockito.when(application.authenticateOauthRequest(org.mockito.Matchers.any(HttpRequest.class))).thenReturn(oauthRequestAuthenticator);

		Mockito.when(oauthRequestAuthenticator.withTtl(60 * acessTokenExpirationInMinutes)).thenReturn(accessTokenRequestAuthenticator);

		Mockito.when(accessTokenRequestAuthenticator.execute()).thenReturn(resultToken);

		TokenResponse tokenResponse = Mockito.mock(TokenResponse.class);
		Mockito.when(resultToken.getTokenResponse()).thenReturn(tokenResponse);

		Mockito.when(tokenResponse.toJson()).thenReturn("{'accesstoken':'abc'}");

		String accessToken = stormpathAuth.generateAccessToken(apiKeyToken);

		assertThat(accessToken, is("{'accesstoken':'abc'}"));
	}

	@Test(expected = WebApplicationException.class)
	public void testgenerateAccessTokenException() {
		String apiKeyToken = "testapiKeyToken";

		Mockito.when(application.authenticateOauthRequest(org.mockito.Matchers.any(HttpRequest.class))).thenThrow(
				ResourceExceptionFixture.newResourceException(2, "message", "developerMessage"));

		stormpathAuth.generateAccessToken(apiKeyToken);

	}

	@Test
	public void testauthenticateRequest() {
		HttpServletRequest request = HttpServletRequestFixture.newHttpServletRequest();

		Account account = AccountFitures.createEmptyAccount();

		ApiAuthenticationResult apiAuthenticationResult = Mockito.mock(ApiAuthenticationResult.class);

		Mockito.when(application.authenticateApiRequest(request)).thenReturn(apiAuthenticationResult);
		Mockito.when(apiAuthenticationResult.getAccount()).thenReturn(account);

		stormpathAuth.authenticateRequest(request);

		assertThat(request.getSession().getAttribute("account"), is(account));
	}

	@Test(expected = WebApplicationException.class)
	public void testauthenticateRequestException() {
		HttpServletRequest request = HttpServletRequestFixture.newHttpServletRequest();

		Account account = AccountFitures.createEmptyAccount();

		Mockito.when(application.authenticateApiRequest(request)).thenThrow(
				ResourceExceptionFixture.newResourceException(2, "message", "developerMessage"));

		stormpathAuth.authenticateRequest(request);

	}

	@Test
	public void testgetUser() {

		Account account = AccountFitures.createEmptyAccount();

		User user = stormpathAuth.getUser(account);

		assertThat(user.getEmail(), is(account.getEmail()));
		assertThat(user.getUsername(), is(account.getUsername()));
	}

	@Test(expected = WebApplicationException.class)
	public void testgetUserException() {
		stormpathAuth.getUser(null);
	}

	@Test
	public void testdeleteTokens() {

		Account account = Mockito.mock(Account.class);

		ApiKey apiKey = Mockito.mock(ApiKey.class);

		ApiKeyList apiKeyList = ApiKeyListFixture.newApiKeyList(apiKey);

		Mockito.when(account.getApiKeys()).thenReturn(apiKeyList);

		stormpathAuth.deleteTokens(account);

		Mockito.verify(apiKey).delete();

	}
}
