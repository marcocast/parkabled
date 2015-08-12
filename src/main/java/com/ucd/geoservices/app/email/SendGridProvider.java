package com.ucd.geoservices.app.email;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;

@Component
public class SendGridProvider {

	private final SendGrid sendgrid;

	public SendGridProvider() {
		String smtpAuthUser = Optional.ofNullable(
				System.getenv("SENDGRID_USERNAME")).orElse(
				System.getProperty("SENDGRID_USERNAME"));
		String smtpAuthPassword = Optional.ofNullable(
				System.getenv("SENDGRID_PASSWORD")).orElse(
				System.getProperty("SENDGRID_PASSWORD"));

		this.sendgrid = new SendGrid(smtpAuthUser, smtpAuthPassword);
	}

	public void send(String fromEmail, String toEmail, String subject,
			String htmlContent, BufferedImage img, String fileName) {

		SendGrid.Email email = new SendGrid.Email();
		email.addTo(toEmail);
		email.setFrom(fromEmail);
		email.setSubject(subject);
		email.setText(htmlContent);

		try {
			File file = new File(fileName);
			ImageIO.write(img, getFileType(fileName), file);
			email.addAttachment(file.getName(), file);
			sendgrid.send(email);
			file.delete();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public String getFileType(String fileName) {
		return fileName.substring(fileName.indexOf('.') + 1);
	}
}
