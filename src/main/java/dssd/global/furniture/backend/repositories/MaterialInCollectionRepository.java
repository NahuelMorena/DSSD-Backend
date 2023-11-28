package dssd.global.furniture.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dssd.global.furniture.backend.model.MaterialInCollection;

public interface MaterialInCollectionRepository extends CrudRepository<MaterialInCollection,Long> {
	List<MaterialInCollection> findByCollectionId(Long collectionId);

}
