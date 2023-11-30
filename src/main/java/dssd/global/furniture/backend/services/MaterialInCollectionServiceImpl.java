package dssd.global.furniture.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dssd.global.furniture.backend.model.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.services.interfaces.MaterialInCollectionService;
import dssd.global.furniture.backend.repositories.CollectionRepository;
import dssd.global.furniture.backend.repositories.MaterialInCollectionRepository;
import dssd.global.furniture.backend.repositories.MaterialRepository;

@Service
public class MaterialInCollectionServiceImpl implements MaterialInCollectionService {
	
	@Autowired
	private MaterialInCollectionRepository materialCollRepository;
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private CollectionRepository collectionRepository;

	@Override
	public List<MaterialInCollection> getMaterialsInCollection(Long idCollection) {
		return materialCollRepository.findByCollectionId(idCollection);
	}
	
	@Transactional
	public void updateMaterialInCollection(Long idCollection,String materialName,Integer quantityReserved) {
		Material m=materialRepository.findByNameIgnoreCase(materialName);
		Optional<Collection> c=collectionRepository.findById(idCollection);
		if(c.isPresent()) {
			MaterialInCollection mc=materialCollRepository.findByCollectionAndMaterial(m,c.get());
			mc.setQuantity(mc.getQuantity()-quantityReserved);
			materialCollRepository.save(mc);
		}
	}

}
