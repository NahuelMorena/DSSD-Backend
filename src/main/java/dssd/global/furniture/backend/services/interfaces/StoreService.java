package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.model.Store;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StoreService {

    public List<Store> getAllStores();
    public Optional<Store> getStoreByID(Long id);
}
