package org.nuxeo.sample;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionExpiryFilter implements Filter {

	private static final Log log = LogFactory.getLog(SessionExpiryFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		HttpServletRequest httpReq = (HttpServletRequest) req;
		long currTime = System.currentTimeMillis();
		HttpSession session = httpReq.getSession();				
		long expiryTime = currTime + session.getMaxInactiveInterval() * 1000;
		Cookie cookie = new Cookie("serverTime", "" + currTime);
		cookie.setPath("/");
		httpResp.addCookie(cookie);
		if (httpReq.getRemoteUser() != null) {
			log.debug(String.format("remote user is not null, sessionExpiry is %d",expiryTime));
			cookie = new Cookie("sessionExpiry", "" + expiryTime);
		} else {
			log.debug(String.format("remote user is null, sessionExpiry is NOW %d",currTime));
			cookie = new Cookie("sessionExpiry", "" + currTime);
		}
		cookie.setPath("/");
		httpResp.addCookie(cookie);
		filterChain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}