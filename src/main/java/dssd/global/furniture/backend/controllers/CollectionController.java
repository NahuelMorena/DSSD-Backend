package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.controllers.dtos.*;
import dssd.global.furniture.backend.controllers.dtos.api.OffersByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.ReserveByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO;
import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO.MaterialRequest;
import dssd.global.furniture.backend.controllers.dtos.request.OffersToReserveDTO;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.FurnitureInCollection;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.interfaces.CloudApiService;
import dssd.global.furniture.backend.services.interfaces.CollectionService;

import org.apache.http.HttpStatus;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private final String baseUrl = "/api/collections";

    @GetMapping(baseUrl + "/get-collections")
    public HttpEntity<List<CollectionDTO>> getCollections(){
		List<Collection> collections = this.collectionService.getAllCollections();
		List<CollectionDTO> collectionDTOs = this.convertToDTOs(collections);
        return ResponseEntity.ok(collectionDTOs);
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PostMapping(baseUrl + "/create-collection")
    public ResponseEntity<Collection> createCollection(@RequestBody CollectionDTO request) {
    	Collection newCollection=null;
    	if(request.getFurnitures().size()>0 && request.getDate_end_manufacture()!=null && request.getDate_start_manufacture()!=null && request.getEstimated_release_date()!=null){
    			newCollection=this.collectionService.createCollection(request.getDate_start_manufacture(),request.getDate_end_manufacture(),
    			request.getEstimated_release_date(), request.getFurnitures(), request.getUnits());
    	}else {
    			return ResponseEntity.badRequest().build();	
    			}
    	try {
			Long caseId=this.bonitaService.startCase();
			this.bonitaService.assignTaskToUser(caseId, newCollection);
		} catch (ProcessDefinitionNotFoundException | ProcessActivationException | ProcessExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
    	return ResponseEntity.ok(newCollection);
    	
    }
    
    @CrossOrigin(origins="http://localhost:4200",allowCredentials="true")
    @PostMapping(value=baseUrl + "/establishMaterials")
    public ResponseEntity<String> establishMaterials(@RequestBody MaterialRequestDTO request){
    	HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
    	Optional<Collection> collection=this.collectionService.getCollectionByID(request.getCollection_id());
    	if(collection.isPresent()) {
        	collectionService.createMaterialInCollection(collection.get(),request.getMaterials());
        	return new ResponseEntity<String>("Materiales establecidos exitosamente", httpHeaders, HttpStatus.SC_OK);
    	}else {
    		return new ResponseEntity<String>("Error al establecer materiales de la colección", httpHeaders, HttpStatus.SC_BAD_REQUEST);
    	}
 
    	
    	
    }

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/search-material-offers")
	public ResponseEntity<List<OffersByApiDTO>> searchMaterialsOffersAPI(@RequestBody MaterialRequestDTO request) {
		List<OffersByApiDTO> offerts = new ArrayList<>();
		Optional<Collection> collection = this.collectionService.getCollectionByID(request.getCollection_id());
		if (collection.isPresent()){
			LocalDate date = collection.get().getDate_start_manufacture();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedDate = date.format(formatter);

			for (MaterialRequestDTO.MaterialRequest material : request.getMaterials()) {
				offerts.addAll(cloudApiService.getOffersByMaterial(material.getName(), formattedDate));
			}
		}
		return ResponseEntity.ok(offerts);
	}

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/reserve-materials")
	public ResponseEntity<List<ReserveByApiDTO>> reserveMaterials(@RequestBody OffersToReserveDTO request){
		List<ReserveByApiDTO> reserves = new ArrayList<>();
		for (OffersToReserveDTO.Offer offer : request.getOffers()){
			reserves.add(cloudApiService.reserveMaterials(offer));
		}
		return ResponseEntity.ok(reserves);
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
