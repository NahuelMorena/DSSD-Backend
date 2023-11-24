package dssd.global.furniture.backend.controllers;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dssd.global.furniture.backend.controllers.dtos.request.LoginRequest;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
public class AuthController {
	
	
	@Autowired
	private UserServiceImplementation userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request ){
		boolean isValid=this.userService.login(loginRequest.getUsername(),loginRequest.getPassword());
		HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
		if(isValid) {
			HttpSession session=request.getSession();
			if(session.isNew()) {
				session.setAttribute("username",loginRequest.getUsername());
			}
		    return new ResponseEntity<String>("Credenciales válidas", httpHeaders, HttpStatus.SC_OK);
		}else {
			return new ResponseEntity<String>("Credenciales inválidas", httpHeaders, HttpStatus.SC_UNAUTHORIZED);
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		session.invalidate();
		HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
	    return new ResponseEntity<String>("Sesión cerrada exitosamente", httpHeaders, HttpStatus.SC_OK);
	}
	
	

}
