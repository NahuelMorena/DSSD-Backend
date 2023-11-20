package dssd.global.furniture.backend.filters;

import java.io.IOException;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class AuthFilter implements Filter {
	
	public AuthFilter() {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		if(session!=null && session.getAttribute("username")!=null) {
			chain.doFilter(request, response);
		}
		else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}


}
