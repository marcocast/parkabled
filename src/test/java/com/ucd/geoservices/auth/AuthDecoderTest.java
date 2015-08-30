package com.ucd.geoservices.auth;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.glassfish.jersey.internal.util.Base64;
import org.junit.Test;

public class AuthDecoderTest {

	@Test
	public void testdecodeBasic() {

		Optional<String[]> emailPassword = AuthDecoder.decodeBasic(Base64.encodeAsString("test@gmail.com:password123"));

		assertThat(emailPassword.get()[0], is("test@gmail.com"));
		assertThat(emailPassword.get()[1], is("password123"));
	}

	@Test
	public void testdecodeBasicNull() {
		Optional<String[]> emailPassword = AuthDecoder.decodeBasic(null);
		assertThat(emailPassword.isPresent(), is(false));
	}

}
