package dssd.global.furniture.backend.repositories;

import dssd.global.furniture.backend.model.Material;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MaterialRepository extends CrudRepository<Material,Long> {
	
	public Material findByNameIgnoreCase(String name);
}
