package dssd.global.furniture.backend.services;

import dssd.global.furniture.backend.model.Store;
import dssd.global.furniture.backend.repositories.StoreRepository;
import dssd.global.furniture.backend.services.interfaces.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImplementation implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> getAllStores() {
        return (List<Store>) storeRepository.findAll();
    }
}
