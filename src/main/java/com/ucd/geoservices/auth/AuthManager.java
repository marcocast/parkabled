package com.ucd.geoservices.auth;

import javax.servlet.http.HttpServletRequest;

import com.ucd.geoservices.model.User;

public interface AuthManager {

	Integer REFRESH_TOKEN_EXPIRATION_DAYS = 60;

	public abstract void deleteTokens(Object accountObject);

	public abstract User getUser(Object accountObject);

	public abstract void authenticateRequest(HttpServletRequest request);

	public abstract String generateAccessToken(String apiKeyToken);

	public abstract String getRefreshToken(String email, String password);

	public abstract User create(User user);

	public abstract void deleteUser(Object accountObject);

}
