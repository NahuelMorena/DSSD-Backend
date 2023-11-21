package dssd.global.furniture.backend.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		if(session!=null && session.getAttribute("username")!=null) {
			return true;
		}
		else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
    }

}
