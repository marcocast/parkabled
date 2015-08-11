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
@XmlRootElement(name = "user")
@XmlType(name = "")
@Wither
@AllArgsConstructor
@EqualsAndHashCode
public class User {

	@XmlElement(name = "email")
	@Getter
	private final String email;

	@XmlElement(name = "password")
	@Getter
	private final String password;

	@XmlElement(name = "username")
	@Getter
	private final String username;

	@XmlElement(name = "status")
	@Getter
	private final String status;

	public User() {
		this.email = null;
		this.password = null;
		this.username = null;
		this.status = null;

	}

}
