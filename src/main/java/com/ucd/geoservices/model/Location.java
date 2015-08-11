package com.ucd.geoservices.model;

import java.util.Map;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;

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

	@XmlElement(name = "name")
	@Getter
	private final String name;

	@XmlElement(name = "totalLocationsHere")
	@Getter
	private final String totalLocationsHere;

	public Location() {
		this.id = null;
		this.coordinates = null;
		this.name = null;
		this.totalLocationsHere = null;
	}

	public Map<String, String> getMetaData() {

		Map<String, String> metaData = Maps.newHashMap();
		Optional.ofNullable(name).filter(element -> !element.isEmpty())
				.map(element -> metaData.put(LocationMetaData.NAME.toString(), element));

		metaData.put(LocationMetaData.TOTAL_LOCATIONS_HERE.toString(),
				Optional.ofNullable(totalLocationsHere).orElseGet(() -> "1"));

		return metaData;
	}
}
