package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.model.Furniture;
import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import dssd.global.furniture.backend.services.interfaces.FurnitureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FurnitureController {
	
	@Autowired
	private FurnitureService furnitureService;
	
	@Autowired
	private UserServiceImplementation userService;
	
	
    private final String baseUrl = "/api/furnitures";

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping(baseUrl + "/get-furnitures")
    public HttpEntity<List<Furniture>> getFurnitures(HttpServletRequest req){
    	HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.CREATIVE)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
        return ResponseEntity.ok(this.furnitureService.getAllFurniture());
    }
    
    
}
