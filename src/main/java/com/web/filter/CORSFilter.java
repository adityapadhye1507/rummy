package com.web.filter;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CORSFilter
 */
@WebFilter("/*")
public class CORSFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public CORSFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * To add the Access control headers in every request
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final Logger log = Logger.getLogger(CORSFilter.class.getName());
		// place your code here
		if (response instanceof HttpServletResponse) {
			//log.info("Adding headers");
			HttpServletResponse http = (HttpServletResponse) response;
			http.addHeader("Access-Control-Allow-Origin", "*");
			http.addHeader("Access-Control-Allow-Credentials", "true");
			http.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
			http.addHeader("Access-Control-Allow-Headers", "Content-Type, AuthToken, GameId, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
