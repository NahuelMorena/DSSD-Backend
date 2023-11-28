package dssd.global.furniture.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.services.interfaces.MaterialInCollectionService;
import dssd.global.furniture.backend.repositories.MaterialInCollectionRepository;

@Service
public class MaterialInCollectionServiceImpl implements MaterialInCollectionService {
	
	@Autowired
	private MaterialInCollectionRepository materialCollRepository;

	@Override
	public List<MaterialInCollection> getMaterialsInCollection(Long idCollection) {
		return materialCollRepository.findByCollectionId(idCollection);
	}

}
