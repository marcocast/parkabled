package com.ucd.geoservices.service;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.email.SendGridProvider;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.User;

@Component
public class EmailService {

	@Autowired
	private SendGridProvider emailProvider;

	public void sendEmailWithImage(User user, Location parkingLocation, BufferedImage img, String fileName) {
		String content = "Hi Officer,";
		content += "\r\n";
		content += "I would like to report a violation on the following parking spot :";
		content += "\r\n";
		content += "";
		content += "\r\n";
		content += "Coordinates : " + parkingLocation.getCoordinates().getLongitude() + " , " + parkingLocation.getCoordinates().getLatitude() + "";
		content += "\r\n";
		content += "Regards";
		content += "\r\n";
		content += user.getUsername();
		emailProvider.send(user.getEmail(), "marcocast@gmail.com", "Report Parking Violation", content, img, fileName);

	}

}
