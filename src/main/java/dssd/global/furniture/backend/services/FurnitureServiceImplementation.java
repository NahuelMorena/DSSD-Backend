package dssd.global.furniture.backend.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dssd.global.furniture.backend.model.Category;
import dssd.global.furniture.backend.model.Furniture;
import dssd.global.furniture.backend.repositories.FurnitureRepository;
import dssd.global.furniture.backend.services.interfaces.FurnitureService;

@Service
public class FurnitureServiceImplementation implements FurnitureService {
	
	@Autowired
	private FurnitureRepository furnitureRepository;
	
	@Transactional
	public Furniture createFurniture(String model_name,String description,Category category) {
		Furniture furniture=new Furniture(model_name,description,category);
		return this.furnitureRepository.save(furniture);
	}
	
	 @Transactional
	    public List<Furniture> getAllFurniture() {
	        return (List<Furniture>) this.furnitureRepository.findAll();
	    }

}
