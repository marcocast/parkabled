package com.ucd.geoservices.rest.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aol.micro.server.auto.discovery.FilterConfiguration;
import com.ucd.geoservices.app.Main;
import com.ucd.geoservices.auth.AuthManager;

@Component
public class AuthorizationFilter implements Filter, FilterConfiguration {

	@Autowired
	private AuthManager authManager;

	@Override
	public String[] getMapping() {
		String appname = "/" + Main.APPNAME;
		return new String[] { appname + "/resources/locations/add/*", appname + "/resources/locations/remove", appname + "/resources/user/details",
				appname + "/resources/user/logout", appname + "/resources/user/delete" };
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			authManager.authenticateRequest(request);
			chain.doFilter(request, response);
		} catch (WebApplicationException e) {
			response.sendError(e.getResponse().getStatus(), e.getResponse().getEntity().toString());
		}
	}

	@Override
	public void destroy() {
	}

}
