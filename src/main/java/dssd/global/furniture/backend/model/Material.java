package dssd.global.furniture.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@JsonIgnoreProperties({"materialCollection"})
@Table(name="materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "material")
    private Set<MaterialInCollection> collections;

    public Material(){}

    public Material(String name, String description, Set<MaterialInCollection> collection){
        this.name = name;
        this.description = description;
        this.collections = collection;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MaterialInCollection> getCollections() {
        return collections;
    }

    public void setCollections(Set<MaterialInCollection> collections) {
        this.collections = collections;
    }
}
