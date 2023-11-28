package dssd.global.furniture.backend.controllers;


import java.util.List;
import org.apache.http.HttpStatus;
import org.bonitasoft.engine.api.APIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import dssd.global.furniture.backend.controllers.dtos.TaskDTO;
import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class BonitaController {
	
	@Autowired
	BonitaService bonitaService;
	
	@Autowired
	UserServiceImplementation userService;
	
	
	private final APIClient bonitaAPIClient;
	
	public BonitaController(APIClient bonitaAPIClient) {
        this.bonitaAPIClient = bonitaAPIClient;
    }
	
	private final String url="/api/bonita";
	
	@GetMapping(url+"/getTasksStablishMaterials")
	public ResponseEntity<List<TaskDTO>> getAllStablishMaterials( HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		List<TaskDTO> l=this.bonitaService.getAllTaskByName("Establecer materiales");
		return ResponseEntity.ok(l);
	}
	
	@GetMapping(url+"/planDistributionOrders")
	public ResponseEntity<List<TaskDTO>> getAllPlanDistributionOrders(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if (! userService.getRole(username).equals(Rol.COMMERCIAL)) {
			return new ResponseEntity("No se permiten las acciones", null, HttpStatus.SC_FORBIDDEN);
		}
		List<TaskDTO> tasks = this.bonitaService.getAllTaskByName("Planificar ordenes de distribución");
		return ResponseEntity.ok(tasks);
	}

	@GetMapping(url+"/launchCollection")
	public ResponseEntity<List<TaskDTO>> getAllLaunchCollection(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		if (! userService.getRole(username).equals(Rol.COMMERCIAL)){
			return new ResponseEntity("No se permiten las acciones", null, HttpStatus.SC_FORBIDDEN);
		}
		List<TaskDTO> tasks = this.bonitaService.getAllTaskByName("Lanzar la colección al mercado");
		return ResponseEntity.ok(tasks);
	}

	@GetMapping(url + "/getTasksQueryApi")
	public ResponseEntity<List<TaskDTO>> getAllQueryApi(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		List<TaskDTO> l=this.bonitaService.getAllTaskByName("Consultar API en busqueda de materiales necesarios");
		return ResponseEntity.ok(l);
	}
	
	@PostMapping(url+"/nextTaskAPIQuery/{idCase}")
	public ResponseEntity<?> nextTaskAPIQuery(@PathVariable Long idCase,HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		this.bonitaService.nextTaskAPIQuery(idCase);
		return new ResponseEntity("Avance exitoso",null,HttpStatus.SC_OK);
	}

}
