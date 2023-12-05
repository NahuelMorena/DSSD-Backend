package dssd.global.furniture.backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dssd.global.furniture.backend.controllers.dtos.CollectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO;
import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO.MaterialRequest;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.Furniture;
import dssd.global.furniture.backend.model.FurnitureInCollection;
import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.repositories.CollectionRepository;
import dssd.global.furniture.backend.repositories.FurnitureInCollectionRepository;
import dssd.global.furniture.backend.repositories.MaterialInCollectionRepository;
import dssd.global.furniture.backend.repositories.MaterialRepository;
import dssd.global.furniture.backend.services.interfaces.CollectionService;

@Service
public class CollectionServiceImplementation implements CollectionService {

	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	private FurnitureInCollectionRepository furnitureInCollectionRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MaterialInCollectionRepository materialInCollectionRepository;
	
	@Transactional
	public Collection createCollection(LocalDate date_start_manufacture, LocalDate date_end_manufacture,
									   LocalDate estimated_release_date, List<Furniture> furnitures, Integer units) {
		Collection collection=new Collection(date_start_manufacture,date_end_manufacture,estimated_release_date, units);
		Collection savedCollection = collectionRepository.save(collection);
		for(Furniture furniture: furnitures) {
    		FurnitureInCollection fc=new FurnitureInCollection(savedCollection,furniture);
    		furnitureInCollectionRepository.save(fc);
    	}
		return savedCollection;
	}
	
	@Transactional
	public List<Collection> getAllCollections(){
		return (List<Collection>)collectionRepository.findAll();
	}

	@Transactional
	public Optional<Collection> getCollectionByID(Long id) {
		return this.collectionRepository.findById(id);
	}

	@Transactional
	public void createMaterialInCollection(Collection collection, List<MaterialRequest> lm) {
		for(MaterialRequest material: lm) {
			Material m=materialRepository.findByNameIgnoreCase(material.getName());
			MaterialInCollection mc=new MaterialInCollection(collection,m,material.getQuantity(),Long.valueOf(0));
			materialInCollectionRepository.save(mc);
			}
	}

	@Transactional
	public Collection rescheduleCollection(Collection collection) {
		return collectionRepository.save(collection);
	}

}
