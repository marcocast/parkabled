package com.ucd.geoservices.auth;

import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

public class AuthDecoder {

	public static String[] decodeBasic(String basicAuthEncoded) {

		return Optional.of(basicAuthEncoded).map(withBasic -> withBasic.replaceFirst("[B|b]asic ", ""))
				.map(withoutBasic -> DatatypeConverter.parseBase64Binary(withoutBasic))
				.filter(decodedBytes -> decodedBytes != null && decodedBytes.length > 0)
				.map(decodedBytes -> new String(decodedBytes).split(":", 2)).orElse(new String[0]);

	}

}