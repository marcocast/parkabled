package com.ucd.geoservices.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ucd.geoservices.email.SendGridProvider;
import com.ucd.geoservices.model.LocationMetaData;
import com.ucd.geoservices.model.ReportRequest;
import com.ucd.geoservices.model.User;

@Component
public class EmailService {

	@Value("${reportEmailTo}")
	private String reportEmailTo;

	@Value("${reportEmailSubject}")
	private String reportEmailSubject;

	@Autowired
	private SendGridProvider emailProvider;

	public void sendEmailWithImage(User user, ReportRequest reportRequest, InputStream inputStream, String fileName) {
		StringBuilder content = new StringBuilder("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		content .append( "<html>");
		content .append( "<head>");
		content .append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		content .append( "<title>Park-abled Report Email</title>");
		content .append( "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>");
		content .append( "</head>");
		content .append( "<body style=\"margin: 0; padding: 0;\">");
		content .append( "<h1>");
		content .append( "");
		content .append( "</h1>");
		content .append( "<p>");
		content .append( "Hi Officer,");
		content .append( "<BR>");
		content .append( "I would like to report a violation on the following parking place reserved for disabled people :");
		content .append( "</p>");
		content .append( "<p>");
		content .append( "Address : " + reportRequest.getLocation().getMetadata().get(LocationMetaData.NAME.toString()));
		content .append( "</p>");
		content .append( "<p>");
		content .append( "Coordinates : " + reportRequest.getLocation().getCoordinates().getLongitude() + " , " + reportRequest.getLocation().getCoordinates().getLatitude() + "");
		content .append( "</p>");
		content .append( "<p>");
		content .append( "The violation happened at this date and time : " + new Timestamp(reportRequest.getTimestamp()));
		content .append( "<BR>");
		content .append( user.getUsername());
		content .append( "</p>");
		Optional.ofNullable(reportRequest.getComments()).ifPresent(comments-> addComments(comments,content));
		content .append( "<p>");
		content .append( "Regards");
		content .append( "<BR>");
		content .append( user.getUsername());
		content .append( "</p>");
		content .append( "</body>");
		content .append( "</html>");

		emailProvider.send(user.getEmail(), reportEmailTo, reportEmailSubject, content.toString(), inputStream, fileName);

	}

	private void addComments(String comments, StringBuilder content) {
		content .append( "<p>");
		content .append( "Notes:");
		content .append( "<BR>");
		content .append( comments);
		content .append( "</p>");
	}

}
