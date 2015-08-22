package com.ucd.geoservices.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.email.SendGridProvider;
import com.ucd.geoservices.model.Location;
import com.ucd.geoservices.model.LocationMetaData;
import com.ucd.geoservices.model.User;

@Component
public class EmailService {

	@Value("${reportEmailTo}")
	private String reportEmailTo;

	@Value("${reportEmailSubject}")
	private String reportEmailSubject;

	@Autowired
	private SendGridProvider emailProvider;

	public void sendEmailWithImage(User user, Location parkingLocation, InputStream inputStream, String fileName) {
		String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		content += "<html>";
		content += "<head>";
		content += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
		content += "<title>Park-abled Report Email</title>";
		content += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>";
		content += "</head>";
		content += "<body style=\"margin: 0; padding: 0;\">";
		content += "<h1>";
		content += "";
		content += "</h1>";
		content += "<p>";
		content += "Hi Officer,";
		content += "<BR>";
		content += "I would like to report a violation on the following parking place reserved for disabled people :";
		content += "</p>";
		content += "<p>";
		content += "Address : " + parkingLocation.getMetadata().get(LocationMetaData.NAME.toString());
		content += "</p>";
		content += "<p>";
		content += "Coordinates : " + parkingLocation.getCoordinates().getLongitude() + " , " + parkingLocation.getCoordinates().getLatitude() + "";
		content += "</p>";
		content += "<p>";
		content += "Regards";
		content += "<BR>";
		content += user.getUsername();
		content += "</p>";
		content += "</body>";
		content += "</html>";

		emailProvider.send(user.getEmail(), reportEmailTo, reportEmailSubject, content, inputStream, fileName);

	}

}
