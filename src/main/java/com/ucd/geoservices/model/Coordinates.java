package com.ucd.geoservices.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "coordinates")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Coordinates {

	@XmlElement(name = "longitude")
	@Getter
	private final double longitude;

	@XmlElement(name = "latitude")
	@Getter
	private final double latitude;

	public Coordinates() {
		this.longitude = 0;
		this.latitude = 0;
	}

}
