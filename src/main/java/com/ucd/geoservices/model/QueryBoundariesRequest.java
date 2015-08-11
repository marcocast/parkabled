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
@XmlRootElement(name = "queryBoundariesRequest")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@EqualsAndHashCode
public class QueryBoundariesRequest {

	@XmlElement(name = "topLeftCoordinates")
	@Getter
	private final Coordinates topLeftCoordinates;

	@XmlElement(name = "bottomRightCoordinates")
	@Getter
	private final Coordinates bottomRightCoordinates;

	public QueryBoundariesRequest() {
		this.topLeftCoordinates = null;
		this.bottomRightCoordinates = null;
	}

}
