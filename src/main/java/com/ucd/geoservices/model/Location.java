package com.ucd.geoservices.model;

import java.util.Map;

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

import com.google.common.collect.Maps;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "location")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Location {

	@XmlElement(name = "id")
	@Getter
	private final String id;

	@XmlElement(name = "coordinates")
	@Getter
	private final Coordinates coordinates;

	@XmlElement(name = "metadata")
	@Getter
	private final Map<String, String> metadata;

	public Location() {
		this.id = null;
		this.coordinates = null;
		this.metadata = Maps.newHashMap();
	}

}
