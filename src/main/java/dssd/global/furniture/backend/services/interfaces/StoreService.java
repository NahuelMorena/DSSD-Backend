package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.model.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreService {

    public List<Store> getAllStores();
}
