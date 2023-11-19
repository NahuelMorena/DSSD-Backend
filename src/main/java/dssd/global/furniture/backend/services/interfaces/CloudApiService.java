package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.controllers.dtos.api.DateSpaceApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.OffersByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.OffersToReserveDTO;
import dssd.global.furniture.backend.controllers.dtos.api.ReserveByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.ReserveDateSpaceRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CloudApiService {

    public String authenticate();
    public List<OffersByApiDTO> getOffersByMaterial(String materialName, String date);
    public ReserveByApiDTO reserveMaterials(OffersToReserveDTO.Offer offer);
    public List<DateSpaceApiDTO> getDateSpaces();
    public DateSpaceApiDTO reserveDateSpace(ReserveDateSpaceRequestDTO reserveDateSpace);
}
