package com.ucd.geoservices.email;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;

@Component
public class SendGridProvider {

	private final SendGrid sendgrid;

	public SendGridProvider() {
		String sendgridUser = Optional.ofNullable(System.getenv("SENDGRID_USERNAME")).orElse(System.getProperty("SENDGRID_USERNAME"));
		String sendgridPassword = Optional.ofNullable(System.getenv("SENDGRID_PASSWORD")).orElse(System.getProperty("SENDGRID_PASSWORD"));

		this.sendgrid = new SendGrid(sendgridUser, sendgridPassword);
	}

	public void send(String fromEmail, String toEmail, String subject, String htmlContent, BufferedImage img, String fileName) {

		SendGrid.Email email = new SendGrid.Email();
		email.addTo(toEmail);
		email.setFrom(fromEmail);
		email.setSubject(subject);
		email.setText(htmlContent);
		Optional<ByteArrayOutputStream> byteArrayOutputStream = Optional.empty();
		try {
			byteArrayOutputStream = Optional.of(new ByteArrayOutputStream());
			ImageIO.write(img, getFileType(fileName), byteArrayOutputStream.get());
			
			email.addAttachment(fileName, new ByteArrayInputStream(byteArrayOutputStream.get().toByteArray()) );
			sendgrid.send(email);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			byteArrayOutputStream.ifPresent(it -> {
				try {
					it.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			});
		}

	}

	public String getFileType(String fileName) {
		return fileName.substring(fileName.indexOf('.') + 1);
	}
}
