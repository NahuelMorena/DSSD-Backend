package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.model.Category;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.Furniture;
import dssd.global.furniture.backend.model.FurnitureInCollection;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    private final String baseUrl = "/api/collections";

    @GetMapping(baseUrl + "/get-collections")
    public HttpEntity<List<Collection>> getCollections(){
        //Se crea una collección y se envia en una lista para
        //demostrar que funciona el endpoint

        //Creo manualmente dos mueble
        Furniture newFurniture = new Furniture();
        newFurniture.setModel_name("Sofa de cuero");
        newFurniture.setDescription("Un cómodo sofá de cuero");
        newFurniture.setCategory(Category.SOFA);

        Furniture newFurniture2 = new Furniture();
        newFurniture2.setModel_name("Silla de madera");
        newFurniture2.setDescription("Una cómoda silla de madera");
        newFurniture2.setCategory(Category.SILLA);

        //Creo manualmente una collección
        Collection newCollection = new Collection();
        newCollection.setDate_end_manufacture(LocalDate.of(2023, 12, 15));
        newCollection.setDate_start_manufacture(LocalDate.of(2023, 12, 15));
        newCollection.setEstimated_release_date(LocalDate.of(2023, 12, 15));

        //Creo la relación entre colección y mueble
        FurnitureInCollection fc = new FurnitureInCollection(newCollection, newFurniture);
        Set<FurnitureInCollection> fcList = new HashSet<>();
        fcList.add(fc);
        newCollection.setFurnitures(fcList);

        FurnitureInCollection fc2 = new FurnitureInCollection(newCollection, newFurniture2);
        newCollection.getFurnitures().add(fc2);

        //Creo la lista de collecciones a enviar
        List<Collection> collectionList = new ArrayList<>();
        collectionList.add(newCollection);

        return ResponseEntity.ok(collectionList);
    }

    @PostMapping(baseUrl + "/create-collection")
    public ResponseEntity<Collection> createCollection(@RequestBody Collection request) {
        System.out.println("-------------------------------");
        System.out.println(request);
        System.out.println("-------------------------------");
        return ResponseEntity.ok(request);
    }
}
