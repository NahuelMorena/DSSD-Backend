package dssd.global.furniture.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dssd.global.furniture.backend.model.BonitaInstanceFail;

public interface BonitaInstanceRepository extends CrudRepository<BonitaInstanceFail, Long> {
	
	@Query
	public BonitaInstanceFail getById(Long id);
}
