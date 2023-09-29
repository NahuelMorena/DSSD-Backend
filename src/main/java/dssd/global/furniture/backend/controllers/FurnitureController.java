package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.model.Category;
import dssd.global.furniture.backend.model.Furniture;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FurnitureController {
    private final String baseUrl = "/api/furnitures";

    @GetMapping(baseUrl + "/get-furnitures")
    public HttpEntity<List<Furniture>> getFurnitures(){
        //Se crean dos muebles y se envia en una lista para demostrar
        //que funciona el endpoint
        List<Furniture> furnitureList = new ArrayList<>();

        Furniture newFurniture = new Furniture();
        newFurniture.setModel_name("Sofa de cuero");
        newFurniture.setDescription("Un cómodo sofá de cuero");
        newFurniture.setCategory(Category.SOFA);

        furnitureList.add(newFurniture);

        Furniture newFurniture2 = new Furniture();
        newFurniture2.setModel_name("Silla de madera");
        newFurniture2.setDescription("Una cómoda silla de madera");
        newFurniture2.setCategory(Category.SILLA);

        furnitureList.add(newFurniture2);

        return ResponseEntity.ok(furnitureList);
    }
}
