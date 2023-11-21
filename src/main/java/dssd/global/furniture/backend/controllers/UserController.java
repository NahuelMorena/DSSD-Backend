package dssd.global.furniture.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {
	@Autowired
	private UserServiceImplementation userService;
	
	@Autowired
	private BonitaService bonitaService;
	
	private final String url="/api/users";
	
	@GetMapping(url+"/getRolSession")
	public ResponseEntity<String> getRolSession(){
		String username=this.bonitaService.getUsernameCurrentUser();
		String rol=this.userService.getRole(username).name();
		return ResponseEntity.ok(rol);	
	}
}
