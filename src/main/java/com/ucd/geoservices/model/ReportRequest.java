package com.ucd.geoservices.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Wither;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "reportRequest")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@EqualsAndHashCode
public class ReportRequest {

	@XmlElement(name = "location")
	@Getter
	private final Location location;

	@XmlElement(name = "timestamp")
	@Getter
	private final long timestamp;
	
	@XmlElement(name = "comments")
	@Getter
	private final String comments;

	public ReportRequest() {
		this.location = null;
		this.timestamp = 0;
		this.comments = "";
	}
	
	

}
