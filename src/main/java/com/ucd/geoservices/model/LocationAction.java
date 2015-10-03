package com.ucd.geoservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationAction {

	private String locationId;

	private String username;

	private ACTION action;

	private long timestamp;

}
