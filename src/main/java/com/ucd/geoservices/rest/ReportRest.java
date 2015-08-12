package com.ucd.geoservices.rest;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.rest.JacksonUtil;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.User;
import com.ucd.geoservices.service.EmailService;
import com.ucd.geoservices.service.UserService;


@Path("/report")
@Component
@Rest(isSingleton = true)
public class ReportRest {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@POST
	@Path("send")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response sendJpg(
			@Context HttpServletRequest request,
			@FormDataParam("location") String locationJson,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader)
			throws Exception {

		User user = userService.getUser(request);

		Location location = JacksonUtil.convertFromJson(locationJson,
				Location.class);

		BufferedImage img = ImageIO.read(fileInputStream);

		emailService.sendEmailWithImage(user, location, img,
				contentDispositionHeader.getFileName());

		return Response.ok().build();
	}

}
