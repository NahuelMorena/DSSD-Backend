package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.controllers.dtos.api.MaterialApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO.MaterialRequest;
import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.services.BonitaService;
import dssd.global.furniture.backend.services.MaterialInCollectionServiceImpl;
import dssd.global.furniture.backend.services.UserServiceImplementation;
import dssd.global.furniture.backend.services.interfaces.MaterialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    
    @Autowired
    private UserServiceImplementation userService;
    
    @Autowired
    private MaterialInCollectionServiceImpl materialCollService;

    private final String baseUrl = "/api/materials";

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping(baseUrl + "/get-materials")
    public HttpEntity<List<Material>> getMaterials(HttpServletRequest req){
		HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
        return ResponseEntity.ok(this.materialService.getAllMaterials());
    }
	
	@GetMapping(baseUrl + "/getMaterialsCollection/{collectionId}")
	public HttpEntity<List<MaterialRequest>> getMaterialsCollection(@PathVariable Long collectionId,HttpServletRequest req){
		HttpSession session=req.getSession(false);
		String username=(String)session.getAttribute("username");
		if(! userService.getRole(username).equals(Rol.OPERATION)) {
			return new ResponseEntity("No se permiten las acciones",null, HttpStatus.SC_FORBIDDEN);
		}
		List<MaterialInCollection> listMatInCol=this.materialCollService.getMaterialsInCollection(collectionId);
		List<MaterialRequest> listMatInColDTO=new ArrayList<MaterialRequest>();
		for(MaterialInCollection material:listMatInCol) {
			Material mat=material.getMaterial();
			listMatInColDTO.add(new MaterialRequest(mat.getName(),material.getQuantity()));
		}
		return ResponseEntity.ok(listMatInColDTO);
	}

}