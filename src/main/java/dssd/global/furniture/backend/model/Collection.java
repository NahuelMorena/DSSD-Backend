package dssd.global.furniture.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "collection")
    private Set<FurnitureInCollection> furnitures;

    @OneToMany(mappedBy = "collection")
    @JsonManagedReference
    private Set<MaterialInCollection> materials;

    private LocalDate date_start_manufacture;

    private LocalDate date_end_manufacture;

    private LocalDate estimated_release_date;

    private Integer units;

    public Collection(){}

    public Collection(Long id, LocalDate date_start_manufacture, LocalDate date_end_manufacture, LocalDate estimated_release_date, Set<FurnitureInCollection> furnitures) {
        this.id = id;
        this.date_start_manufacture = date_start_manufacture;
        this.date_end_manufacture = date_end_manufacture;
        this.estimated_release_date = estimated_release_date;
        this.furnitures = furnitures;
    }
    
    

	public Collection(LocalDate date_start_manufacture,LocalDate date_end_manufacture, LocalDate estimated_release_date, Integer units) {
		this.date_start_manufacture = date_start_manufacture;
		this.date_end_manufacture = date_end_manufacture;
		this.estimated_release_date = estimated_release_date;
        this.units = units;
	}

	public Set<FurnitureInCollection> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(Set<FurnitureInCollection> furnitures) {
        this.furnitures = furnitures;
    }

    public Long getID() {
        return id;
    }
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

    public Set<MaterialInCollection> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<MaterialInCollection> materials) {
        this.materials = materials;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", furnitures=" + furnitures +
                ", date_start_manufacture=" + date_start_manufacture +
                ", date_end_manufacture=" + date_end_manufacture +
                ", estimated_release_date=" + estimated_release_date +
                '}';
    }
}
