package dssd.global.furniture.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.model.Collection;

public interface MaterialInCollectionRepository extends CrudRepository<MaterialInCollection,Long> {
	List<MaterialInCollection> findByCollectionId(Long collectionId);
	
	@Query("SELECT m FROM MaterialInCollection m WHERE m.material= :mat and m.collection= :col")
	MaterialInCollection findByCollectionAndMaterial(Material mat,Collection col);

}
