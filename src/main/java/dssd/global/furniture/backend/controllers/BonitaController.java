package dssd.global.furniture.backend.controllers;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.util.APITypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dssd.global.furniture.backend.controllers.dtos.ExecuteTaskDTO;
import dssd.global.furniture.backend.controllers.dtos.MessageDTO;
import dssd.global.furniture.backend.services.BonitaService;

@RestController
public class BonitaController {
	
	@Autowired
	BonitaService bonitaService;
	
	@GetMapping("/")
	public void login(){
		
	}
	
	@GetMapping("/api/bonita/startCase")
	public ResponseEntity<?> startCase(){
		
		this.
		return ResponseEntity.ok(new MessageDTO("Caso creado"));
		
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
}
