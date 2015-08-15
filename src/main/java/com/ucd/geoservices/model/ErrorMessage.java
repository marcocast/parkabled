package com.ucd.geoservices.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Wither;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errorMessage")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorMessage {

	private final String message;

	public ErrorMessage() {
		this.message = "Error";
	}

}
