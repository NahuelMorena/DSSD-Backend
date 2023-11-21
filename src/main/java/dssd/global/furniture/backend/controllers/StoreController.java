package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.model.Store;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.interfaces.StoreService;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;
    
    @Autowired
    private BonitaService bonitaService;

    private final String baseUrl = "/api/stores";

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping(baseUrl + "/get-stores")
    public HttpEntity<List<Store>> getStores(){
    	if(!this.bonitaService.currentUserCanAccess(Rol.COMMERCIAL)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
        return ResponseEntity.ok(this.storeService.getAllStores());
    }
}
