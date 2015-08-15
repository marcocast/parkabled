package com.ucd.geoservices.app;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.module.ModuleBean;

@Configuration
public class ConfigureModuleService {

	@Bean(name = "geoservicesdModule")
	public ModuleBean configure() {
		int port = new Integer(Optional.ofNullable(System.getProperty("service.port")).orElse("8080"));
		return ModuleBean.builder().port(port).module(Main.GEOSERVICES_MODULE).host("https://" + Main.APPNAME + ".herokuapp.com:").build();
	}

}
