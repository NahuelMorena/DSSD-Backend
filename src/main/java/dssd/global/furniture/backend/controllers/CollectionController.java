package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.controllers.dtos.CollectionDTO;
import dssd.global.furniture.backend.controllers.dtos.MaterialRequestDTO;
import dssd.global.furniture.backend.controllers.dtos.OffertsByApiDTO;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.FurnitureInCollection;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.interfaces.CollectionService;

import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CollectionController {
	
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private BonitaService bonitaService;

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
    			request.getEstimated_release_date(), request.getFurnitures());
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

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@PostMapping(baseUrl + "/search-material-offers")
	public ResponseEntity<List<OffertsByApiDTO>> searchMaterialsOffersAPI(@RequestBody MaterialRequestDTO request) {
		System.out.println("entro a este endpoint");
		System.out.println(request.getCollection_id());
		System.out.println(request.getMaterials().get(0).getName());
		System.out.println(request.getMaterials().get(0).getQuantity());
		System.out.println("--------------");
		return null;
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
