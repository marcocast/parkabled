package com.ucd.geoservices.auth;

import javax.servlet.http.HttpServletRequest;

import com.ucd.geoservices.model.User;

public interface AuthManager {

	public abstract void deleteTokens(Object accountObject);

	public abstract User getUser(Object accountObject);

	public abstract void authenticateRequest(HttpServletRequest request);

	public abstract String generateAccessToken(String apiKeyToken);

	public abstract String getRefreshToken(String email, String password);

	public abstract User create(User user);

	public abstract void deleteUser(Object accountObject);

	public abstract User sendPasswordResetEmail(String email);

	public abstract User passwordReset(String resetToken, String newPassword);

}
