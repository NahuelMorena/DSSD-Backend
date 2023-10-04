package dssd.global.furniture.backend.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApplicationAPI;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessEnablementException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.expression.Expression;
import org.bonitasoft.engine.expression.ExpressionBuilder;
import org.bonitasoft.engine.expression.ExpressionType;
import org.bonitasoft.engine.expression.InvalidExpressionException;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.operation.LeftOperand;
import org.bonitasoft.engine.operation.LeftOperandBuilder;
import org.bonitasoft.engine.operation.Operation;
import org.bonitasoft.engine.operation.OperationBuilder;
import org.bonitasoft.engine.operation.OperatorType;
import org.bonitasoft.engine.search.Order;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;
import org.bonitasoft.engine.session.APISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import dssd.global.furniture.backend.utils.Constantes;




@Service
@RestController
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
	
	public long getProcessDefinitionId​(String name, String version) {
		try {
			return 	this.getProcessAPI().getProcessDefinitionId(name, version);
		} catch (ProcessDefinitionNotFoundException e) {
			System.out.println("NO SE ENCONTRO LA DEFINICION DEL PROCESO POR NOMBRE Y VERSION");
			e.printStackTrace();
			return -1;
		}
	}
	
	public ProcessDefinition getProcessDefinition(long id) {
		try {
			return this.getProcessAPI().getProcessDefinition(id);
		} catch (ProcessDefinitionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("NO SE ENCONTRO LA DEFINICION DEL PROCESO POR ID");
			return null;
		}
	}
	
	public void enableProcess(ProcessDefinition processDefinition) {
		try {
			
			this.getProcessAPI().enableProcess(processDefinition.getId());
			System.out.println("A new process was enabled: " + processDefinition.getId());
			
		} catch (ProcessDefinitionNotFoundException e) {
			System.out.println("NO SE ENCONTRO LA DEFINICION DEL PROCESO al hacer enable");
			e.printStackTrace();
		} catch (ProcessEnablementException e) {
			System.out.println("Paso algo al intentar hacer enable process definition");
			e.printStackTrace();
		}
		
	}
	
	public void startCase() throws ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException {
		 
		ProcessDefinition processDefinition = this.getProcessDefinition(
				this.getProcessDefinitionId​("Proceso de planificación de colección de muebles", "2.0"));
		System.out.println(
				"ID DEL PROCESO: " + processDefinition.getId() + " NOMBRE DEL PROCESO" + processDefinition.getName());

		//start the process. Tras hacer esto en el localhost de bonita en la pestaña de "cases" deberia aparecer uno nuevo.
		final ProcessInstance processInstance = this.getProcessAPI().startProcess(processDefinition.getId());
	    System.out.println("A new process instance was started with id: " + processInstance.getId());
	}
	
	public SearchResult<ProcessDeploymentInfo> getLast100DeployedProcess() throws SearchException {
		final SearchOptions searchOptions = new SearchOptionsBuilder(0, 100).sort(ProcessDeploymentInfoSearchDescriptor.DEPLOYMENT_DATE, Order.DESC).done();
		final SearchResult<ProcessDeploymentInfo> deploymentInfoResults = this.getProcessAPI().searchProcessDeploymentInfos(searchOptions);
		return deploymentInfoResults;
	}
	
/* Set string variables and start a process instance
 * In this example, createInstance takes the process definition name, the process version, 
 * a map of text variables and their values. The startProcess method, which creates the process instance,
 * takes a list of operations, not a map of variables, so the map must be converted into a list of operations
 * that will set the values of the variables in the process instance. The example calls buildAssignOperation 
 * for each variable in turn, to build an operation that will assign the value to the variable when the process 
 * instance is created. Each operation is built as an assignment expression.*/
	
	public void createInstance(String processDefinitionName, String processVersion, Map<String, Object> variables) {
	    ProcessAPI processAPI;
	    try {
	        processAPI = apiClient.getProcessAPI();
	        long processDefinitionId = processAPI.getProcessDefinitionId(processDefinitionName, processVersion);

	        List<Operation> listOperations = new ArrayList<>();
	        for (String variableName : variables.keySet()) {
	            if (variables.get(variableName) != null) {
	               Operation operation = buildAssignOperation(variableName, variables.get(variableName).toString(),
	                    String.class.getName(), ExpressionType.TYPE_CONSTANT);
	               listOperations.add(operation);
	            }
	       }
	       processAPI.startProcess(processDefinitionId, listOperations, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private Operation buildAssignOperation(final String dataInstanceName, final String newConstantValue,
	    final String className, final ExpressionType expressionType) throws InvalidExpressionException {
	    final LeftOperand leftOperand = new LeftOperandBuilder().createNewInstance().setName(dataInstanceName).done();
	    final Expression expression = new ExpressionBuilder().createNewInstance(dataInstanceName).setContent(newConstantValue).setExpressionType(expressionType.name()).setReturnType(className).done();
	    final Operation operation = new OperationBuilder().createNewInstance().setOperator("=").setLeftOperand(leftOperand).setType(OperatorType.ASSIGNMENT).setRightOperand(expression).done();
	    return operation;
	}
	
	
/*	Set variables of any type and start a process instance
	In this example, createCase takes the process definition name,
	 the process version, a map of variable names and objects, and 
	 the session identifier. The startProcess method, which creates 
	 the process instance, takes a list of operations, not a map of 
	 variables, so the map must be converted into a list of operations 
	 that will set the values of the variables in the process instance. 
	 For each variable in turn, the example builds an expression that assigns 
	 the value to the variable to the object supplied in the map, specifying 
	 the data type by identifying the class of the object. These expressions 
	 are concatenated into a list of operations, which is used to initialize 
	 the variables when the process instance is created.
	*/
	
	public void createCase(String processDefinitionName, String processVersion, Map<String, Object> variables, ProcessAPI processAPI) {
	    try {
	        long processDefinitionId = processAPI.getProcessDefinitionId(processDefinitionName, processVersion);
	        // ----- create list of operations -----
	        List<Operation> listOperations = new ArrayList<>();
	        Map<String, Serializable> listVariablesSerializable = new HashMap<>();

	        for (String variableName : variables.keySet()) {
	            Object value = variables.get(variableName);
	            if (value != null && value instanceof Serializable) {
	                Serializable valueSerializable = (Serializable) value;

	                variableName = variableName.toLowerCase();
	                Expression expr = new ExpressionBuilder().createExpression(variableName, variableName, value.getClass().getName(), ExpressionType.TYPE_INPUT);
	                Operation op = new OperationBuilder().createSetDataOperation(variableName, expr);
	                listOperations.add(op);
	                listVariablesSerializable.put(variableName, valueSerializable);
	            }
	        }

	        // ----- start process instance -----
	        processAPI.startProcess(processDefinitionId, listOperations, listVariablesSerializable);

	        // System.out.println("*** End Create Case ****");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
/*	Create a map of variables and values and start a process instance
	Create a map specifying the values of the variables required to start a case,
	 then pass it to the instantiateProcess method, as shown in the following example:*/
	
	public void instantiateProcess(String processDefinitionName, String processVersion, Map<String, Serializable> variables)  {
	    try {
	        ProcessAPI processAPI = apiClient.getProcessAPI();
	        long processId = processAPI.getProcessDefinitionId(processDefinitionName, processVersion);
	        processAPI.startProcess(processId, variables);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

	public void executeTask(long userId, long taskInstanceId) throws UserTaskNotFoundException, FlowNodeExecutionException, ContractViolationException{
		//No hace nada aun
		String s =this.getProcessAPI()
				.getActivities(
						this.getProcessDefinitionId​(Constantes.NOMBRE_PROCESO, Constantes.VERSION_PROCESO), 
						0, 
						12
				)
		.toString();
		System.out.println("Lista: " + s);
		
		//apiClient.getProcessAPI().assignAndExecuteUserTask(userId, getProcess, null);
		//this.getProcessAPI().executeUserTask(userId, taskInstanceId, null);
	}
}
