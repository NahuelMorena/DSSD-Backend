package dssd.global.furniture.backend.services;

import dssd.global.furniture.backend.model.Material;
import dssd.global.furniture.backend.repositories.MaterialRepository;
import dssd.global.furniture.backend.services.interfaces.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialServiceImplementation implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Transactional
    public List<Material> getAllMaterials() {
        return (List<Material>) materialRepository.findAll();
    }
}
