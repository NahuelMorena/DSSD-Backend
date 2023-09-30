package dssd.global.furniture.backend.services;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApplicationAPI;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.search.Order;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.bonitasoft.engine.session.APISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class BonitaService {
	@Autowired
	APIClient apiClient;
	
	public IdentityAPI getIdentityAPI() {
		return this.apiClient.getIdentityAPI();
	}
	
	public ApplicationAPI getApplicationAPI() {
		return this.apiClient.getApplicationAPI();
	}

	public ProcessAPI getProcessAPI() {
		return this.apiClient.getProcessAPI();
	}
	
	public APISession getSession() {
		return this.apiClient.getSession();
	}
	
	public User getCurrentLoggedInUser() {
		try {
			return this.getIdentityAPI().getUser(this.apiClient.getSession().getUserId());
		} catch (UserNotFoundException e) {
			System.out.println("NO SE ENCONTRO EL USUARIO " + apiClient.getSession().getUserName());
			return null;
		}
	}
	
	public void startProcess(ProcessDefinition processDefinition) throws ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException {
	    
	    ProcessInstance processInstance = this.getProcessAPI().startProcess(processDefinition.getId());
	}
	
	public SearchResult<ProcessDeploymentInfo> getLast100DeployedProcess() throws SearchException {
		final SearchOptions searchOptions = new SearchOptionsBuilder(0, 100).sort(ProcessDeploymentInfoSearchDescriptor.DEPLOYMENT_DATE, Order.DESC).done();
		final SearchResult<ProcessDeploymentInfo> deploymentInfoResults = this.getProcessAPI().searchProcessDeploymentInfos(searchOptions);
		return deploymentInfoResults;
	}
}
