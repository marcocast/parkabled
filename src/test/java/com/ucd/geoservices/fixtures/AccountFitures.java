package com.ucd.geoservices.fixtures;

import java.util.Map;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountOptions;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.account.EmailVerificationToken;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyCriteria;
import com.stormpath.sdk.api.ApiKeyList;
import com.stormpath.sdk.api.ApiKeyOptions;
import com.stormpath.sdk.application.ApplicationCriteria;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupCriteria;
import com.stormpath.sdk.group.GroupList;
import com.stormpath.sdk.group.GroupMembership;
import com.stormpath.sdk.group.GroupMembershipList;
import com.stormpath.sdk.provider.ProviderData;
import com.stormpath.sdk.tenant.Tenant;

public class AccountFitures {

	public static Account createEmptyAccount() {

		return new Account() {

			String email;
			String username;
			String password;
			AccountStatus status;

			@Override
			public CustomData getCustomData() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void delete() {
				// TODO Auto-generated method stub

			}

			@Override
			public void save() {
				// TODO Auto-generated method stub

			}

			@Override
			public String getHref() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Account setUsername(String arg0) {
				this.username = arg0;
				return this;
			}

			@Override
			public Account setSurname(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Account setStatus(AccountStatus arg0) {
				this.status = arg0;
				return this;
			}

			@Override
			public Account setPassword(String arg0) {
				this.password = arg0;
				return this;
			}

			@Override
			public Account setMiddleName(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Account setGivenName(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Account setEmail(String arg0) {
				this.email = arg0;
				return this;
			}

			@Override
			public Account saveWithResponseOptions(AccountOptions arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isMemberOfGroup(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return username;
			}

			@Override
			public Tenant getTenant() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getSurname() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public AccountStatus getStatus() {
				// TODO Auto-generated method stub
				return AccountStatus.ENABLED;
			}

			@Override
			public ProviderData getProviderData() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getMiddleName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GroupList getGroups(GroupCriteria arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GroupList getGroups(Map<String, Object> arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GroupList getGroups() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GroupMembershipList getGroupMemberships() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getGivenName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getFullName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public EmailVerificationToken getEmailVerificationToken() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getEmail() {
				// TODO Auto-generated method stub
				return email;
			}

			@Override
			public Directory getDirectory() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApplicationList getApplications(ApplicationCriteria arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApplicationList getApplications(Map<String, Object> arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApplicationList getApplications() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApiKeyList getApiKeys(ApiKeyCriteria arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApiKeyList getApiKeys(Map<String, Object> arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApiKeyList getApiKeys() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApiKey createApiKey(ApiKeyOptions arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApiKey createApiKey() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GroupMembership addGroup(Group arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
