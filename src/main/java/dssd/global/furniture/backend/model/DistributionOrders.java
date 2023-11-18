package dssd.global.furniture.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "distribution_orders")
public class DistributionOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_collection")
    private Collection collection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_store")
    @JsonBackReference
    private Store store;

    private Integer units;

    public DistributionOrders() {}

    public DistributionOrders(Collection collection, Store store, Integer units){
        this.collection = collection;
        this.store = store;
        this.units = units;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }
}