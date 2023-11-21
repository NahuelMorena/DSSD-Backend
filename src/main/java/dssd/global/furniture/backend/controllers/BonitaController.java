package dssd.global.furniture.backend.controllers;


import java.util.List;
import org.apache.http.HttpStatus;
import org.bonitasoft.engine.api.APIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import dssd.global.furniture.backend.controllers.dtos.TaskStablishMaterialsDTO;
import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.UserServiceImplementation;

@RestController
public class BonitaController {
	
	@Autowired
	BonitaService bonitaService;
	
	
	private final APIClient bonitaAPIClient;
	
	public BonitaController(APIClient bonitaAPIClient) {
        this.bonitaAPIClient = bonitaAPIClient;
    }
	
	private final String url="/api/bonita";
	
	@GetMapping(url+"/getTasksStablishMaterials")
	public ResponseEntity<List<TaskStablishMaterialsDTO>> getAllStablishMaterials() {
		if(! this.bonitaService.currentUserCanAccess(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		List<TaskStablishMaterialsDTO> l=this.bonitaService.getAllStablishMaterials();
		return ResponseEntity.ok(l);
	}
	
	
	
}
