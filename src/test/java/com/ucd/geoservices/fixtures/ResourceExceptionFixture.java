package com.ucd.geoservices.fixtures;

import com.stormpath.sdk.error.Error;
import com.stormpath.sdk.resource.ResourceException;

public class ResourceExceptionFixture {

	public static ResourceException newResourceException(int status, String message, String developerMessage) {
		return new ResourceException(new Error() {

			@Override
			public int getStatus() {
				return status;
			}

			@Override
			public String getMoreInfo() {
				return null;
			}

			@Override
			public String getMessage() {
				return message;
			}

			@Override
			public String getDeveloperMessage() {
				return developerMessage;
			}

			@Override
			public int getCode() {
				return 0;
			}
		});
	}
}
