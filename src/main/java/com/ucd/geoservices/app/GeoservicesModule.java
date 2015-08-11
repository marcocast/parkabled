package com.ucd.geoservices.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableMap;

public class GeoservicesModule implements Module {

	private final String context;

	public GeoservicesModule(String context) {
		this.context = context;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public Map<String, Filter> getFilters(ServerData data) {
		return ImmutableMap.of();
	}

	@Override
	public List<Class> getDefaultResources() {
		List<Class> resources = new ArrayList<Class>(ConfigurableModule.builder().build().getDefaultResources());
		resources.add(MultiPartFeature.class);

		return resources;
	}

}
