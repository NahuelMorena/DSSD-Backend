package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.controllers.dtos.*;
import dssd.global.furniture.backend.controllers.dtos.api.DateSpaceApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.OffersByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.ReserveByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.*;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.DistributionOrders;
import dssd.global.furniture.backend.model.FurnitureInCollection;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.model.Store;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.MaterialInCollectionServiceImpl;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import dssd.global.furniture.backend.services.interfaces.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CollectionController {
	
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private BonitaService bonitaService;
	@Autowired
	private CloudApiService cloudApiService;
	@Autowired
	private DistributionOrderService distributionOrderService;
	@Autowired
	private UserServiceImplementation userService;
	@Autowired
	private MaterialInCollectionServiceImpl materialCollService;
	@Autowired
	private MaterialInCollectionService materialInCollectionService;
	@Autowired
	private StoreService storeService;

    private final String baseUrl = "/api/collections";

    @GetMapping(baseUrl + "/get-collections")
    public HttpEntity<List<CollectionDTO>> getCollections(){
		List<Collection> collections = this.collectionService.getAllCollections();
		List<CollectionDTO> collectionDTOs = this.convertToDTOs(collections);
        return ResponseEntity.ok(collectionDTOs);
    }
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/get-collection")
	public ResponseEntity<Optional<Collection>> getCollectionById(@RequestBody Map<String, Long> request) {
		return ResponseEntity.ok(this.collectionService.getCollectionByID(request.get("id")));
	}

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PostMapping(baseUrl + "/create-collection")
    public ResponseEntity<Collection> createCollection(@RequestBody CollectionDTO request,HttpServletRequest req) {
    	HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.CREATIVE)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
    	Collection newCollection=null;
    	if(request.getFurnitures().size()>0 && request.getDate_end_manufacture()!=null && request.getDate_start_manufacture()!=null
    			&& request.getEstimated_release_date()!=null && request.getUnits()!=null && request.getUnits()>0
    			&& request.getDate_start_manufacture().isBefore(request.getDate_end_manufacture()) && request.getDate_end_manufacture().isBefore(request.getEstimated_release_date())){
    			newCollection=this.collectionService.createCollection(request.getDate_start_manufacture(),request.getDate_end_manufacture(),
    			request.getEstimated_release_date(), request.getFurnitures(), request.getUnits());
    	}else {
    			return ResponseEntity.badRequest().build();	
    			}
    	try {
			Long caseId=this.bonitaService.startCase();
			this.bonitaService.assignTaskToUser(caseId, newCollection, request.getMail());
		} catch (ProcessDefinitionNotFoundException | ProcessActivationException | ProcessExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
    	return ResponseEntity.ok(newCollection);
    }
    
    @CrossOrigin(origins="http://localhost:4200",allowCredentials="true")
    @PostMapping(value=baseUrl + "/establishMaterials")
    public ResponseEntity<String> establishMaterials(@RequestBody MaterialRequestDTO request,HttpServletRequest req){
    	HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
    	HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
    	Optional<Collection> collection=this.collectionService.getCollectionByID(request.getCollection_id());
    	if(collection.isPresent()) {
        	collectionService.createMaterialInCollection(collection.get(),request.getMaterials());
			bonitaService.nextBonitaTask(request.getProcess_instance_id(), "Establecer materiales");
        	return new ResponseEntity<String>("Materiales establecidos exitosamente", httpHeaders, HttpStatus.SC_OK);
    	}else {
    		return new ResponseEntity<String>("Error al establecer materiales de la colección", httpHeaders, HttpStatus.SC_BAD_REQUEST);
    	}
 
    	
    	
    }

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@GetMapping(baseUrl + "/search-material-offers/{collectionId}")
	public ResponseEntity<List<OffersByApiDTO>> searchMaterialsOffersAPI(@PathVariable Long collectionId,
			@RequestParam(name = "dateStart", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateForm	,HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		List<OffersByApiDTO> offerts = new ArrayList<>();
		Optional<Collection> collection = this.collectionService.getCollectionByID(collectionId);
		if (collection.isPresent()){
			if(! this.cloudApiService.isLogged()) {
				this.cloudApiService.authenticate();
			}
			LocalDate date = collection.get().getDate_start_manufacture();
			if(! dateForm.before(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))&&(dateForm.after(new Date()))) {
				return new ResponseEntity("La fecha ingresada no es antes que la fecha de inicio de fabricación o después de la de hoy",null,HttpStatus.SC_BAD_REQUEST);
			}
			SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = formatter.format(dateForm);
			List<MaterialInCollection> listMatInCol=this.materialCollService.getMaterialsInCollection(collectionId);
			for (MaterialInCollection material :listMatInCol) {
				offerts.addAll(cloudApiService.getOffersByMaterial(material.getMaterial().getName(), formattedDate));
			}
		}
		return ResponseEntity.ok(offerts);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/reserve-materials/{collectionId}")
	public ResponseEntity<List<ReserveByApiDTO>> reserveMaterials(@PathVariable Long collectionId,@RequestBody OffersToReserveDTO request,HttpServletRequest req){
		HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		List<ReserveByApiDTO> reserves = new ArrayList<>();
		for (OffersToReserveDTO.Offer offer : request.getOffers()){
			ReserveByApiDTO r=cloudApiService.reserveMaterials(offer,collectionId);
			if(r!=null) {
				reserves.add(r);
				this.materialCollService.updateMaterialInCollection(collectionId,offer.getNameMaterial(),offer.getQuantity());
			}
		}
		return ResponseEntity.ok(reserves);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/set-distribution-order")
	public ResponseEntity<List<DistributionOrders>> setDistributionOrder(@RequestBody OrdersRequestDTO request){
		List<DistributionOrders> orders = new ArrayList<>();
		Collection collection = collectionService.getCollectionByID(request.getCollection_id())
				.orElseThrow(() -> new RuntimeException("La colección no se encontro"));
		for (OrdersRequestDTO.OrderRequest order : request.getOrders()){
			Store store = storeService.getStoreByID(order.getId_store())
							.orElseThrow(() -> new RuntimeException("La tienda no se encontro"));
			orders.add(distributionOrderService.setDistributionOrder(store, collection, order.getQuantity()));
		}
		bonitaService.nextBonitaTask(request.getProcess_instance_id(), "Planificar ordenes de distribución");
		return ResponseEntity.ok(orders);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@GetMapping(baseUrl + "/get-dateSpaces")
	public ResponseEntity<List<DateSpaceApiDTO>> getDateSpaces(){
		if(! this.cloudApiService.isLogged()) {
			this.cloudApiService.authenticate();
		}
		return ResponseEntity.ok(cloudApiService.getDateSpaces());
	}

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/reserve-dateSpace")
	public ResponseEntity<DateSpaceApiDTO> reserveDateSpace(@RequestBody DatesSpaceRequestDTO request){
		if(! this.cloudApiService.isLogged()) {
			this.cloudApiService.authenticate();
		}
		List<ReserveDateSpaceRequestDTO.ReserveID> list = new ArrayList<>();
		for(ReserveByApiDTO reserve : this.cloudApiService.getByIdCollection(request.getCollection_id())){
			list.add(new ReserveDateSpaceRequestDTO.ReserveID(reserve.getId()));
		}
		ReserveDateSpaceRequestDTO reserves = new ReserveDateSpaceRequestDTO(request.getDateSpace_id(),list);
		DateSpaceApiDTO dateSpace = cloudApiService.reserveDateSpace(reserves);
		this.bonitaService.nextBonitaTask(request.getProcess_instance_id(), "Establecer reserva de espacio de fabricación");
		return ResponseEntity.ok(dateSpace);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/launch-to-market")
	public ResponseEntity<Collection> launchToMarket(@RequestBody LaunchRequestDTO request){
		Collection collection = collectionService.getCollectionByID(request.getCollection_id())
				.orElseThrow(() -> new RuntimeException("La colección no se encontro"));
		bonitaService.nextBonitaTask(request.getProcess_instance_id(), "Lanzar la colección al mercado");
		return ResponseEntity.ok(collection);
	}

	//Metodos de consulta
	@CrossOrigin(origins = "http://localhost:60000", allowCredentials = "true")
	@GetMapping(baseUrl + "/checkExistenceOfDelays/{id}")
	public ResponseEntity<String> checkExistenceOfDelays(@PathVariable Long id){
		System.out.println("Peticion para consultar existencia de retrasos de materiales");
		Collection collection = collectionService.getCollectionByID(id)
				.orElseThrow(() -> new RuntimeException("La colección no se encontro"));
		List<MaterialInCollection> materialInCollections = materialInCollectionService.getMaterialsInCollection(collection.getID());
		List<Long> list = new ArrayList<>();
		for (MaterialInCollection mic : materialInCollections){
			list.add(mic.getId());
		}
		Boolean state = cloudApiService.checkExistenceOfDelays(list);
		System.out.println("Valor devuelto: "+state);
		System.out.println("---------------------------------------------------------");
		return ResponseEntity.ok(state.toString());
	}

	@GetMapping(baseUrl + "/checkArrivalOfAllMaterials/{id}")
	public ResponseEntity<String> checkArrivalOfAllMaterials(@PathVariable Long id){
		System.out.println("Peticion para consultar llegada de todos los materiales");
		Collection collection = collectionService.getCollectionByID(id)
				.orElseThrow(() -> new RuntimeException("La colección no se encontro"));
		List<MaterialInCollection> materialInCollections = materialInCollectionService.getMaterialsInCollection(collection.getID());
		Boolean state = cloudApiService.checkArrivalOfAllMaterials(materialInCollections.get(0).getId());
		System.out.println("Valor devuelto: "+state);
		System.out.println("---------------------------------------------------------");
		return ResponseEntity.ok(state.toString());
	}

	@GetMapping(baseUrl + "/check/{id}")
	public ResponseEntity<String> checkAvailableManufacturingSpace(@PathVariable Long id){
		System.out.println("Peticion para consultar si quedan espacios de fabricacion disponibles");
		Boolean state = cloudApiService.checkAvailableManufacturingSpace();
		System.out.println("Valor devuelto: "+state);
		System.out.println("---------------------------------------------------------");
		if(!state) {
			this.bonitaService.changeStateToCancelled(id);
		}
		return ResponseEntity.ok(state.toString());
	}

	@GetMapping(baseUrl + "/manufacturingCompletionInquiry/{id}")
	public ResponseEntity<String> manufacturingCompletionInquiry(@PathVariable Long id){
		System.out.println("Peticion para consultar si finalizo el proceso de fabricacion");
		Collection collection = collectionService.getCollectionByID(id)
				.orElseThrow(() -> new RuntimeException("La colección no se encontro"));
		List<MaterialInCollection> materialInCollections = materialInCollectionService.getMaterialsInCollection(collection.getID());
		//Boolean state = cloudApiService.manufacturingCompletionInquiry(materialInCollections.get(0).getId());
		Boolean state = cloudApiService.manufacturingCompletionInquiry(1L);
		System.out.println("Valor devuelto: "+state);
		System.out.println("---------------------------------------------------------");
		return ResponseEntity.ok(state.toString());
	}
	/**
	 *
	 * METODOS PRIVADOS
	 *
	 */

	private List<CollectionDTO> convertToDTOs(List<Collection> collections){
		return collections.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	private CollectionDTO convertToDTO(Collection collection) {
		CollectionDTO dto = new CollectionDTO();
		dto.setId(collection.getID());
		dto.setDate_start_manufacture(collection.getDate_start_manufacture());
		dto.setDate_end_manufacture(collection.getDate_end_manufacture());
		dto.setEstimated_release_date(collection.getEstimated_release_date());
		dto.setFurnitures(collection.getFurnitures().stream().map(FurnitureInCollection::getFurniture).collect(Collectors.toList()));
		return dto;
	}
}
