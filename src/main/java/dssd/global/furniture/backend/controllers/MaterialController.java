package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.services.interfaces.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    private final String baseUrl = "/api/materials";

	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping(baseUrl + "/get-materials")
    public HttpEntity<List<Material>> getMaterials(){
        return ResponseEntity.ok(this.materialService.getAllMaterials());
    }
}


