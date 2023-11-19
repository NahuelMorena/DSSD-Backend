package dssd.global.furniture.backend.controllers;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.util.APITypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dssd.global.furniture.backend.controllers.dtos.ExecuteTaskDTO;
import dssd.global.furniture.backend.controllers.dtos.MessageDTO;
import dssd.global.furniture.backend.controllers.dtos.TaskStablishMaterialsDTO;
import dssd.global.furniture.backend.services.BonitaService;

@RestController
public class BonitaController {
	
	@Autowired
	BonitaService bonitaService;
	
	private final String url="/api/bonita";
	
	@GetMapping("/api/bonita/startCase")
	public ResponseEntity<?> startCase(){
		
		try {
			this.bonitaService.startCase();
		} catch (ProcessDefinitionNotFoundException | ProcessActivationException | ProcessExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResponseEntity.badRequest().body(new MessageDTO("Caso creado"));
		}
;		return ResponseEntity.ok(new MessageDTO("Caso creado"));
		
	}
	
	@PostMapping("/api/bonita/executeTask")
	public ResponseEntity<?> executeTask(@RequestBody ExecuteTaskDTO executeTaskDTO ){
		
		try {
			this.bonitaService.executeTask(executeTaskDTO.userId(), executeTaskDTO.taskInstanceId());
		} catch (UserTaskNotFoundException | FlowNodeExecutionException | ContractViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}
	
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@GetMapping(url+"/getTasksStablishMaterials")
	public ResponseEntity<List<TaskStablishMaterialsDTO>> getAllStablishMaterials() {
		List<TaskStablishMaterialsDTO> l=this.bonitaService.getAllStablishMaterials();
		return ResponseEntity.ok(l);
	}
	
}
