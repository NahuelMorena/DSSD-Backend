package dssd.global.furniture.backend.controllers.dtos;

import java.time.LocalDate;
import java.util.List;

import dssd.global.furniture.backend.model.Furniture;
import dssd.global.furniture.backend.model.Material;

public class CollectionDTO {

	private Long id;
	private LocalDate date_start_manufacture;
    private LocalDate date_end_manufacture;
    private LocalDate  estimated_release_date;
	private Integer units;

	private String mail;
    private List<Furniture> furnitures;
	private List<Material> materials;

	public LocalDate getDate_start_manufacture() {
		return date_start_manufacture;
	}

	public void setDate_start_manufacture(LocalDate date_start_manufacture) {
		this.date_start_manufacture = date_start_manufacture;
	}

	public LocalDate getDate_end_manufacture() {
		return date_end_manufacture;
	}

	public void setDate_end_manufacture(LocalDate date_end_manufacture) {
		this.date_end_manufacture = date_end_manufacture;
	}

	public LocalDate getEstimated_release_date() {
		return estimated_release_date;
	}

	public void setEstimated_release_date(LocalDate estimated_release_date) {
		this.estimated_release_date = estimated_release_date;
	}

	public List<Furniture> getFurnitures() {
		return furnitures;
	}

	public void setFurnitures(List<Furniture> furnitures) {
		this.furnitures = furnitures;
	}
    
    public Long getId() { return id;}

	public void setId(Long id){ this.id = id;}

	public List<Material> getMaterials() { return materials; }

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public Integer getUnits() { return units; }

	public void setUnits(Integer units) { this.units = units; }

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
