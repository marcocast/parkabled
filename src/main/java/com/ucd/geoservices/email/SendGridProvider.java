package com.ucd.geoservices.email;

import java.io.InputStream;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.ucd.geoservices.model.ErrorMessage;

@Component
public class SendGridProvider {

	private final SendGrid sendgrid;

	public SendGridProvider() {
		String sendgridUser = Optional.ofNullable(System.getenv("SENDGRID_USERNAME"))
				.orElse(System.getProperty("SENDGRID_USERNAME"));
		String sendgridPassword = Optional.ofNullable(System.getenv("SENDGRID_PASSWORD"))
				.orElse(System.getProperty("SENDGRID_PASSWORD"));

		this.sendgrid = new SendGrid(sendgridUser, sendgridPassword);
	}

	public void send(String fromEmail, String toEmail, String subject, String htmlContent,
			InputStream inputStream, String fileName) {

		SendGrid.Email email = new SendGrid.Email();
		email.addTo(toEmail);
		email.setFrom(fromEmail);
		email.setSubject(subject);
		email.setHtml(htmlContent);

		try {
			email.addAttachment(fileName, inputStream);
			sendgrid.send(email);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(new ErrorMessage(e.getMessage()))
					.type(MediaType.APPLICATION_JSON).build());
		} 
		

	}

	public String getFileType(String fileName) {
		return fileName.substring(fileName.indexOf('.') + 1);
	}
}
