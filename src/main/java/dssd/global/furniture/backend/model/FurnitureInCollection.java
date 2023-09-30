package dssd.global.furniture.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "furniture_in_collection")
public class FurnitureInCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_collection")
    private Collection collection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_furniture")
    private Furniture furniture;

    public FurnitureInCollection() {}

    public FurnitureInCollection(Collection collection, Furniture furniture) {
        this.collection = collection;
        this.furniture = furniture;
    }

    public Furniture getFurniture() {
        return this.furniture;
    }
}
