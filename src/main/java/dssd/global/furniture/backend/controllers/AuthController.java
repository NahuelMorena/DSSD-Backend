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
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
@Controller
public class AuthController {
	
	
	@Autowired
	private UserServiceImplementation userService;
	
	@Autowired
	private BonitaService bonitaService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request ){
		boolean isValid=this.userService.login(loginRequest.getUsername(),loginRequest.getPassword());
		HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
		if(isValid) {
			this.bonitaService.login(loginRequest);
		    return new ResponseEntity<String>("Credenciales válidas", httpHeaders, HttpStatus.SC_OK);
		}else {
			return new ResponseEntity<String>("Credenciales inválidas", httpHeaders, HttpStatus.SC_BAD_REQUEST);
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(){
		boolean ok=this.bonitaService.logout();
		HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
		if(ok) {
			 return new ResponseEntity<String>("Sesión cerrada exitosamente", httpHeaders, HttpStatus.SC_OK);
		}else {
			return new ResponseEntity<String>("Error al cerrar sesión", httpHeaders, HttpStatus.SC_BAD_REQUEST);
		}
	}
	
	

}
