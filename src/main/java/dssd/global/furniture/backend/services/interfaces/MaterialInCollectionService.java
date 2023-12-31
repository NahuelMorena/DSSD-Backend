package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.MaterialInCollection;
import org.springframework.stereotype.Service;
import dssd.global.furniture.backend.model.Material;

import java.util.List;

@Service
public interface MaterialInCollectionService {
    public List<MaterialInCollection> getMaterialsInCollection(Long idCollection);
    public void updateMaterialInCollection(Long idCollection,String materialName,Integer quantityReserved) ;
}
