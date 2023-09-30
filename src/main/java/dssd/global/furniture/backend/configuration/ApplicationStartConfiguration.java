package dssd.global.furniture.backend.configuration;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
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

public class ApplicationStartConfiguration implements ApplicationRunner {
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
		this.bonitaService.getLast100DeployedProcess().getResult().toString());
		
		ProcessDefinition processDefinition = processAPI.getProcessDefinition(this.bonitaService.getLast100DeployedProcess().getResult().get(0).getId());
		System.out.println("A new process was created: " + processDefinition.getName());
		//enable process
		processAPI.enableProcess(processDefinition.getId());
		System.out.println("A new process was enabled: " + processDefinition.getId());
		//processAPI.enableProcess(processDefinition.getId());
		System.out.println("A new process was enabled: " + processDefinition.getId());
		//start the process
		final ProcessInstance processInstance = processAPI.startProcess(processDefinition.getId());
		
	}
	

	    
}
