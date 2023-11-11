package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.model.Material;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialService {

    public List<Material> getAllMaterials();
}
