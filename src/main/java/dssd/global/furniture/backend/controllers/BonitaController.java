package dssd.global.furniture.backend.controllers;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.APIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import dssd.global.furniture.backend.controllers.dtos.TaskStablishMaterialsDTO;
import dssd.global.furniture.backend.controllers.dtos.request.LoginRequest;
import dssd.global.furniture.backend.services.BonitaService;

@RestController
public class BonitaController {
	
	@Autowired
	BonitaService bonitaService;
	
	private final APIClient bonitaAPIClient;
	
	public BonitaController(APIClient bonitaAPIClient) {
        this.bonitaAPIClient = bonitaAPIClient;
    }
	
	private final String url="/api/bonita";
	
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@GetMapping(url+"/getTasksStablishMaterials")
	public ResponseEntity<List<TaskStablishMaterialsDTO>> getAllStablishMaterials() {
		List<TaskStablishMaterialsDTO> l=this.bonitaService.getAllStablishMaterials();
		return ResponseEntity.ok(l);
	}
	
	
	
}
