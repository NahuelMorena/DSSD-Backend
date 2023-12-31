package dssd.global.furniture.backend.services.interfaces;

import dssd.global.furniture.backend.controllers.dtos.api.DateSpaceApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.OffersByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.DatesSpaceRequestDTO;
import dssd.global.furniture.backend.controllers.dtos.request.OffersToReserveDTO;
import dssd.global.furniture.backend.controllers.dtos.api.ReserveByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.ReserveDateSpaceRequestDTO;
import dssd.global.furniture.backend.model.MaterialInCollection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CloudApiService {

    public String authenticate();
    public List<OffersByApiDTO> getOffersByMaterial(String materialName, String date);
    public ReserveByApiDTO reserveMaterials(OffersToReserveDTO.Offer offer, Long collection_id);
    public List<DateSpaceApiDTO> getDateSpaces();
    public DateSpaceApiDTO reserveDateSpace(ReserveDateSpaceRequestDTO reserveDateSpace);
    public Boolean checkExistenceOfDelays(List<Long> list);
    public Boolean checkArrivalOfAllMaterials(Long reserve_id);
    public Boolean checkAvailableManufacturingSpace();
    public Boolean manufacturingCompletionInquiry(Long reserve_id);
    public boolean isLogged();
    public List<ReserveByApiDTO> getByIdCollection(Long collection_id);
}
