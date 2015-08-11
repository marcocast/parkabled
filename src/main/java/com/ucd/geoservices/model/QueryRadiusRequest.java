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
@XmlRootElement(name = "queryRadiusRequest")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@EqualsAndHashCode
public class QueryRadiusRequest {

	@XmlElement(name = "centralCoordinates")
	@Getter
	private final Coordinates centralCoordinates;

	@XmlElement(name = "radius")
	@Getter
	private final double radius;

	public QueryRadiusRequest() {
		this.centralCoordinates = null;
		this.radius = 0;
	}

}
