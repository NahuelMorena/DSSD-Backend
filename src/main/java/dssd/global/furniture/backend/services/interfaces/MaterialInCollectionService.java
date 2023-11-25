package dssd.global.furniture.backend.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.model.MaterialInCollection;

@Service
public interface MaterialInCollectionService {
	public List<MaterialInCollection> getMaterialsInCollection(Long idCollection);
}
