package dssd.global.furniture.backend.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import dssd.global.furniture.backend.controllers.dtos.OffertsByApiDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CloudApiService {

    public String authenticate();
    public List<OffertsByApiDTO> getOffersByMaterial(String materialName, String date);
}
