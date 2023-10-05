package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.controllers.dtos.CollectionDTO;
import dssd.global.furniture.backend.model.Category;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.Furniture;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CollectionController {
	
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private BonitaService bonitaService;
	
	
    private final String baseUrl = "/api/collections";

    @GetMapping(baseUrl + "/get-collections")
    public HttpEntity<List<Collection>> getCollections(){
       return ResponseEntity.ok(this.collectionService.getAllCollections());
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
			this.bonitaService.startCase();
		} catch (ProcessDefinitionNotFoundException | ProcessActivationException | ProcessExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
    	return ResponseEntity.ok(newCollection);
    	
    }
}
