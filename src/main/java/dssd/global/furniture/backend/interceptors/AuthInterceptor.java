package dssd.global.furniture.backend.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import dssd.global.furniture.backend.services.BonitaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	@Autowired
    private BonitaService bonitaService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!bonitaService.isLogged()) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true; // Permite que la solicitud continúe hacia el controlador
    }

}
