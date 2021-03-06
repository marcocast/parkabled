package com.ucd.geoservices.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.service.UserService;

@Path("/user")
@Component
@Rest(isSingleton = true)
public class UserRest {

	@Value("${acessTokenExpirationInMinutes}")
	private Integer acessTokenExpirationInMinutes;

	@Autowired
	private UserService userService;

	@POST
	@Path("create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(@Context HttpServletResponse response, final String userCreateRequest) {
		User user = JacksonUtil.convertFromJson(userCreateRequest, User.class);
		userService.create(user);
		String refreshToken = userService.getRefreshToken(user.getEmail(), user.getPassword());
		addRefreshToken(response, refreshToken);
		return accesstoken(refreshToken);
	}

	@GET
	@Path("login")
	@Produces("application/json")
	public Response login(@HeaderParam("authorization") String auth, @Context HttpServletResponse response) {
		String refreshToken = userService.getRefreshToken(auth);
		addRefreshToken(response, refreshToken);
		return accesstoken(refreshToken);
	}

	@GET
	@Path("passwordreset/{email}")
	@Produces("application/json")
	public Response passwordResetEmail(@PathParam("email") String email) {
		User user = userService.sendPasswordResetEmail(email);
		return Response.ok(JacksonUtil.serializeToJson(user)).build();
	}

	@POST
	@Path("newpassword/{resetToken}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response passwordReset(@Context HttpServletResponse response, @PathParam("resetToken") String resetToken, final String userCreateRequest) {
		User user = JacksonUtil.convertFromJson(userCreateRequest, User.class);
		userService.passwordReset(resetToken, user.getPassword());
		String refreshToken = userService.getRefreshToken(user.getEmail(), user.getPassword());
		addRefreshToken(response, refreshToken);
		return accesstoken(refreshToken);
	}

	@GET
	@Path("logout")
	@Produces("application/json")
	public Response logout(@Context HttpServletRequest request) {
		userService.deleteTokens(request);
		return Response.ok().build();
	}

	@GET
	@Path("accesstoken")
	@Produces("application/json")
	public Response accesstoken(@HeaderParam("refresh_token") String refreshToken) {
		return Response.ok(userService.getAccessToken(refreshToken)).build();
	}

	@GET
	@Path("details")
	@Produces("application/json")
	public Response userDetails(@Context HttpServletRequest request) {
		User user = userService.getUser(request);
		return Response.ok(JacksonUtil.serializeToJson(user)).build();
	}

	@DELETE
	@Path("delete")
	@Produces("application/json")
	public Response deleteUser(@Context HttpServletRequest request) {
		userService.deleteUser(request);
		return Response.ok().build();
	}

	private void addRefreshToken(HttpServletResponse response, String refreshToken) {
		response.setHeader("refresh_token", refreshToken);
		response.setHeader("refresh_token_expires_in_days", acessTokenExpirationInMinutes.toString());
	}
}
