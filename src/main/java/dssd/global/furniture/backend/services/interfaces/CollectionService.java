package dssd.global.furniture.backend.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO;
import dssd.global.furniture.backend.controllers.dtos.request.MaterialRequestDTO.MaterialRequest;
import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.Furniture;


@Service
public interface CollectionService {
	public Collection createCollection(LocalDate date_start_manufacture, LocalDate date_end_manufacture,
									   LocalDate estimated_release_date, List<Furniture> furnitures, Integer units);

	public List<Collection> getAllCollections();

	public Optional<Collection> getCollectionByID(Long id);
	
	public void createMaterialInCollection(Collection collection, List<MaterialRequest> lm);
}
