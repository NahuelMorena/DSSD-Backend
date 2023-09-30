package dssd.global.furniture.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "collection")
    private Set<FurnitureInCollection> furnitures;

    private LocalDate date_start_manufacture;

    private LocalDate date_end_manufacture;

    private LocalDate  estimated_release_date;

    public Collection(){}

    public Collection(Long id, LocalDate date_start_manufacture, LocalDate date_end_manufacture, LocalDate estimated_release_date, Set<FurnitureInCollection> furnitures) {
        this.id = id;
        this.date_start_manufacture = date_start_manufacture;
        this.date_end_manufacture = date_end_manufacture;
        this.estimated_release_date = estimated_release_date;
        this.furnitures = furnitures;
    }

    public Set<FurnitureInCollection> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(Set<FurnitureInCollection> furnitures) {
        this.furnitures = furnitures;
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
