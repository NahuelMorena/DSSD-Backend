package dssd.global.furniture.backend.services.interfaces;


import java.util.List;

import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.model.Category;
import dssd.global.furniture.backend.model.Furniture;

@Service
public interface FurnitureService {
	
	public Furniture createFurniture(String model_name,String description,Category category);
	public List<Furniture> getAllFurniture();
}
