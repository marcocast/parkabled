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
@XmlRootElement(name = "plainAddress")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PlainAddress {

	@XmlElement(name = "country")
	@Getter
	private final String country;

	@XmlElement(name = "city")
	@Getter
	private final String city;

	@XmlElement(name = "street")
	@Getter
	private final String street;

	@XmlElement(name = "number")
	@Getter
	private final String number;

	@XmlElement(name = "totalLocationsHere")
	@Getter
	private final String totalLocationsHere;

	public PlainAddress() {
		this.country = null;
		this.city = null;
		this.street = null;
		this.number = null;
		this.totalLocationsHere = null;
	}

	public String getFullAddressString() {

		return country + " " + city + " " + street + " " + number;
	}

}
