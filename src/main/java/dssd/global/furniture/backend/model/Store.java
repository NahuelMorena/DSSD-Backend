package dssd.global.furniture.backend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String direction;

    private String location;

    @OneToMany(mappedBy = "store")
    private Set<DistributionOrders> orders;

    public Store(){}

    public Store(String name, String direction, String location){
        this.name = name;
        this.direction = direction;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<DistributionOrders> getOrders() {
        return orders;
    }

    public void setOrders(Set<DistributionOrders> orders) {
        this.orders = orders;
    }
}