package com.ucd.geoservices.transformer;

import org.springframework.stereotype.Component;

import com.stormpath.sdk.account.Account;
import com.ucd.geoservices.model.User;

@Component
public class UserTransformer {

	public User fromStormpathUser(Account stormpathUser) {
		return new User(stormpathUser.getEmail(), "", stormpathUser.getUsername(), stormpathUser.getStatus().name());
	}
}
