package dssd.global.furniture.backend.services;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.Iterator;

import dssd.global.furniture.backend.controllers.dtos.TaskDTO;
import dssd.global.furniture.backend.controllers.dtos.apiBonita.ArchivedCases;
import dssd.global.furniture.backend.controllers.dtos.apiBonita.VariableBonita;
import dssd.global.furniture.backend.model.Collection;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApplicationAPI;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.ArchivedActivityInstance;
import org.bonitasoft.engine.bpm.flownode.ArchivedActivityInstanceSearchDescriptor;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ArchivedProcessInstance;
import org.bonitasoft.engine.bpm.process.ArchivedProcessInstancesSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfoSearchDescriptor;
import org.bonitasoft.engine.bpm.process.ProcessEnablementException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.bpm.process.ProcessInstanceNotFoundException;
import org.bonitasoft.engine.bpm.process.impl.internal.ArchivedProcessInstanceImpl;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
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

import dssd.global.furniture.backend.utils.Constantes;

@Service
public class BonitaService {
	
	@Autowired
	APIClient apiClient;
	
	@Autowired
	BonitaApiService bonitaApiService;
	
	@Autowired 
	CollectionServiceImplementation collectionService;
	
	@Autowired
	UserServiceImplementation userService;
	
	
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
	
	public String getUsernameCurrentUser() {
		return this.getCurrentLoggedInUser().getUserName();
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
			e.printStackTrace();
			System.out.println("NO SE ENCONTRO LA DEFINICION DEL PROCESO POR ID");
			return null;
		}
	}
	
	public void assignTaskToUser(Long processInstanceId, Collection collection, String mail){
		HumanTaskInstance humanTask = this.getHumanTaskInstance(processInstanceId, "Planificar colección");
		if(humanTask!=null) {
			Map<String, Serializable> taskVariables = new HashMap<>();
			taskVariables.put("id_collection", collection.getID());
			taskVariables.put("date_start_manufacture", Date.from(collection.getDate_start_manufacture().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			taskVariables.put("date_end_manufacture", Date.from(collection.getDate_end_manufacture().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			taskVariables.put("estimated_release_date", Date.from(collection.getEstimated_release_date().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			taskVariables.put("email", mail);
			this.executeUserTask(humanTask, taskVariables);
		}
	}

	public void nextTaskAPIQuery(Long processInstanceId) {
		HumanTaskInstance humanTask = this.getHumanTaskInstance(processInstanceId, "Consultar API en busqueda de materiales necesarios");
		if(humanTask!=null) {
			Map<String,Serializable> taskVariables=new HashMap<>();
			taskVariables.put("supplier_for_each_material", true);
			this.executeUserTask(humanTask, taskVariables);
		}
	}
	public void nextBonitaTask(Long processInstanceId, String nameTask){
		HumanTaskInstance humanTask = this.getHumanTaskInstance(processInstanceId, nameTask);
		if (humanTask != null){
			this.executeUserTask(humanTask, null);
		} else {
			System.out.println("No encontro instancia de tarea humana");
		}
	}

	private void executeUserTask(HumanTaskInstance humanTask, Map<String, Serializable> taskVariables){
		try {
			this.getProcessAPI().assignUserTask(humanTask.getId(),this.getCurrentLoggedInUser().getId());
			if (taskVariables != null){
				this.getProcessAPI().updateActivityInstanceVariables(humanTask.getId(),taskVariables);
			}
			this.getProcessAPI().executeUserTask(humanTask.getId(),null);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private HumanTaskInstance getHumanTaskInstance(Long processInstanceId, String nameTask){
		List<HumanTaskInstance> pendingTasks = this.getProcessAPI().getPendingHumanTaskInstances(this.getCurrentLoggedInUser().getId(), 0, 30, null);
		HumanTaskInstance humantask = null;
		for (HumanTaskInstance item:  pendingTasks){
			if (processInstanceId.equals(item.getParentProcessInstanceId()) &&
					item.getName().equals(nameTask)
			) {
				humantask = item;
				break;
			}
		}
		return humantask;
	}

	public List<TaskDTO> getAllTaskByName(String nameTask) {
		if(! this.bonitaApiService.isAuthenticated()) {
			this.bonitaApiService.login();
		}
		List<HumanTaskInstance> pendingTasks = this.getProcessAPI().getPendingHumanTaskInstances(this.getCurrentLoggedInUser().getId(), 0, 100, null);
		List<TaskDTO> list=new ArrayList<TaskDTO>();
		for (Iterator<HumanTaskInstance> i = pendingTasks.iterator(); i.hasNext();) {
	        HumanTaskInstance item = i.next();
	        System.out.println(item.getName());
			if(item.getName().equals(nameTask)) {
				String idCase=String.valueOf(item.getParentProcessInstanceId());
				VariableBonita vb=this.bonitaApiService.getIdCollectionCase(idCase);
				Optional<Collection> c=this.collectionService.getCollectionByID(Long.valueOf(vb.getValue()));
				if(c.isPresent()) {
					Collection collection=c.get();
					TaskDTO ts=new TaskDTO(item.getId(),item.getParentProcessInstanceId(),item.getName(),collection.getID()
					, collection.getDate_start_manufacture(),collection.getDate_end_manufacture(), collection.getEstimated_release_date());
					list.add(ts);
				}
			}
		}
		return list;
	}
	

	public Long startCase() throws ProcessDefinitionNotFoundException, ProcessActivationException, ProcessExecutionException {
		 
		ProcessDefinition processDefinition = this.getProcessDefinition(
				this.getProcessDefinitionId​("Proceso de planificación de colección de muebles", "2.0"));
		System.out.println(
				"ID DEL PROCESO: " + processDefinition.getId() + " NOMBRE DEL PROCESO" + processDefinition.getName());
		ProcessInstance processInstance = this.getProcessAPI().startProcess(processDefinition.getId());
	    System.out.println("A new process instance was started with id: " + processInstance.getId());
	    return processInstance.getId();
	}
	
	public SearchResult<ProcessDeploymentInfo> getLast100DeployedProcess() throws SearchException {
		final SearchOptions searchOptions = new SearchOptionsBuilder(0, 100).sort(ProcessDeploymentInfoSearchDescriptor.DEPLOYMENT_DATE, Order.DESC).done();
		final SearchResult<ProcessDeploymentInfo> deploymentInfoResults = this.getProcessAPI().searchProcessDeploymentInfos(searchOptions);
		return deploymentInfoResults;
	}

	public List<ArchivedCases> getArchivedCases() {
		if(! this.bonitaApiService.isAuthenticated()) {
			this.bonitaApiService.login();
		}
		List<ArchivedCases> l=this.bonitaApiService.getArchivedProcessInstances();
		return l;
	}
	
	public void changeState(Long caseId) {
		try {
			this.getProcessAPI().setProcessInstanceState(this.getProcessAPI().getProcessInstance(caseId),"cancelled");
		} catch (UpdateException | ProcessInstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void changeVariable(Long caseId) {
		HumanTaskInstance humanTask = this.getHumanTaskInstance(caseId, "Consultar espacios de fabricación");
		if(humanTask!=null) {
			Map<String,Serializable> taskVariables=new HashMap<>();
			LocalDate fecha = LocalDate.of(2023, 12, 20);
			taskVariables.put("space_reservation_date", Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			try {
				this.getProcessAPI().assignUserTask(humanTask.getId(),this.getCurrentLoggedInUser().getId());
				if (taskVariables != null){
					this.getProcessAPI().updateActivityInstanceVariables(humanTask.getId(),taskVariables);
				}
			} catch (UpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
