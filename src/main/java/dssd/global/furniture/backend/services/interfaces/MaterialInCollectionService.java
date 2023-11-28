package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.MaterialInCollection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialInCollectionService {
    public List<MaterialInCollection> getAllMaterialInCollectionByCollection(Collection collection);
}
