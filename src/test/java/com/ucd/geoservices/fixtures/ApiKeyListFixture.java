package com.ucd.geoservices.fixtures;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeyList;

public class ApiKeyListFixture {

	public static ApiKeyList newApiKeyList(ApiKey apiKey) {
		// TODO Auto-generated method stub
		return new ApiKeyList() {

			List<ApiKey> elements = Lists.newArrayList();

			@Override
			public Iterator<ApiKey> iterator() {
				elements.add(apiKey);
				// TODO Auto-generated method stub
				return elements.iterator();
			}

			@Override
			public String getHref() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ApiKey single() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return elements.size();
			}

			@Override
			public int getOffset() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getLimit() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

}
