package dssd.global.furniture.backend.repositories;
import org.springframework.data.repository.CrudRepository;

import dssd.global.furniture.backend.model.Furniture;


public interface FurnitureRepository extends CrudRepository<Furniture,Long> {
	
}
