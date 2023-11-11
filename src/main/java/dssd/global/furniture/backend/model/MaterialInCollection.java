package dssd.global.furniture.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "material_in_collection")
public class MaterialInCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_collection")
    private Collection collection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_material")
    @JsonBackReference
    private Material material;

    private Integer quantity;

    private Long reserved_material_id_API;

    public MaterialInCollection(){}

    public MaterialInCollection(Collection collection, Material material, Integer quantity, Long reserved_material_id_API){
        this.collection = collection;
        this.material = material;
        this.quantity = quantity;
        this.reserved_material_id_API = reserved_material_id_API;
    }

    public Material getMaterial() {
        return this.material;
    }

}
