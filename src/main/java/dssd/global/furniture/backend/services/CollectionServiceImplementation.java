package dssd.global.furniture.backend.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.Furniture;
import dssd.global.furniture.backend.model.FurnitureInCollection;
import dssd.global.furniture.backend.repositories.CollectionRepository;
import dssd.global.furniture.backend.repositories.FurnitureInCollectionRepository;
import dssd.global.furniture.backend.services.interfaces.CollectionService;

@Service
public class CollectionServiceImplementation implements CollectionService {

	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	private FurnitureInCollectionRepository furnitureInCollectionRepository;
	
	@Transactional
	public Collection createCollection(LocalDate date_start_manufacture, LocalDate date_end_manufacture,
			LocalDate estimated_release_date,List<Furniture> furnitures) {
		Collection collection=new Collection(date_start_manufacture,date_end_manufacture,estimated_release_date);
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
}
