package org.bgi.springboot1;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class SimpleTracingFilter extends GenericFilterBean {

	private static final Log LOG = LogFactory.getLog(SimpleTracingFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		LOG.info(String.format("Request to URI:%s, Method:%s", servletRequest.getRequestURI(), servletRequest.getMethod()));
		chain.doFilter(request, response);
		LOG.info(String.format("Response Status:%d", servletResponse.getStatus()));
	}

	

}
