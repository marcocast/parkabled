package com.ucd.geoservices.auth;

import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthDecoder {

	public static Optional<String[]> decodeBasic(String basicAuthEncoded) {

		return Optional.ofNullable(basicAuthEncoded).map(withBasic -> withBasic.replaceFirst("[B|b]asic ", ""))
				.map(withoutBasic -> DatatypeConverter.parseBase64Binary(withoutBasic))
				.filter(decodedBytes -> decodedBytes != null && decodedBytes.length > 0).map(decodedBytes -> new String(decodedBytes).split(":", 2));

	}

}