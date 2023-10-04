package dssd.global.furniture.backend.configuration;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.flownode.TaskInstance;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.bpm.process.impl.ProcessDefinitionBuilder;
import org.bonitasoft.engine.bpm.process.impl.internal.ProcessDefinitionImpl;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.session.APISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import dssd.global.furniture.backend.services.BonitaService;


@Configuration

public class ApplicationStartExecutions implements ApplicationRunner {
	@Autowired
	BonitaService bonitaService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		IdentityAPI identityAPI= this.bonitaService.getIdentityAPI();
		APISession apiSession=this.bonitaService.getSession();
		ProcessAPI processAPI= this.bonitaService.getProcessAPI();
		
		System.out.println(" ID apiSession ==== " + apiSession.getUserName());
		
		User user= bonitaService.getCurrentLoggedInUser();
		System.out.println("ID usuario:" + user.getId() + " PRIMER NOMBRE:" +user.getFirstName() + " APELLIDO: " + user.getLastName());
		
		System.out.println("LISTA DE PROCESOS DEPLOYADOS:" +
		this.bonitaService.getLast100DeployedProcess().getResult().toString() + " SIZE: " +this.bonitaService.getLast100DeployedProcess().getCount() );
		
		//Conseguir el proceso Pool que he creado en mi bonita
		ProcessDefinition processDefinition = processAPI.getProcessDefinition(
			processAPI.getProcessDefinitionId("Proceso de planificación de colección de muebles", "2.0"));
		System.out.println("ID DEL PROCESO: " + processDefinition.getId() + " NOMBRE DEL PROCESO" + processDefinition.getName());
	
		//start the process. Tras hacer esto en el localhost de bonita en la pestaña de "cases" deberia aparecer uno nuevo.
		final ProcessInstance processInstance = processAPI.startProcess(processDefinition.getId());
		
		/*TaskInstance taskToExecute = processAPI.tasks
		processAPI.assignUserTask(taskToExecute.getId(), apiSession.getUserId());
		*/
	}
	

	    
}
