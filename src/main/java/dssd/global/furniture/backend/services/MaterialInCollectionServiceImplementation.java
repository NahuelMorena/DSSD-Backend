package dssd.global.furniture.backend.services;

import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.MaterialInCollection;
import dssd.global.furniture.backend.repositories.MaterialInCollectionRepository;
import dssd.global.furniture.backend.services.interfaces.MaterialInCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MaterialInCollectionServiceImplementation implements MaterialInCollectionService {

    @Autowired
    MaterialInCollectionRepository materialInCollectionRepository;
    @Transactional
    public List<MaterialInCollection> getAllMaterialInCollectionByCollection(Collection collection) {
        List<MaterialInCollection> list = new ArrayList<>();
        for (MaterialInCollection mic : materialInCollectionRepository.findAll()){
            if (Objects.equals(mic.getCollection().getID(), collection.getID())){
                list.add(mic);
            }
        }
        return list;
    }
}
