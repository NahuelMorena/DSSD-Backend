package dssd.global.furniture.backend.services.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.model.Collection;
import dssd.global.furniture.backend.model.Furniture;


@Service
public interface CollectionService {
	public Collection createCollection(LocalDate date_start_manufacture, LocalDate date_end_manufacture,
									   LocalDate estimated_release_date, List<Furniture> furnitures);
	public List<Collection> getAllCollections();

}
