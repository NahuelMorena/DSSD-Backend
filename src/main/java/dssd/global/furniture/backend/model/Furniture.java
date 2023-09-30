package dssd.global.furniture.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="furnitures")
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model_name;

    private String description;

    @OneToMany(mappedBy = "furniture")
    private Set<FurnitureInCollection> collections;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Furniture() {}

    public Furniture(Long id, String model_name, String description, Set<FurnitureInCollection> collection, Category category) {
        this.id = id;
        this.model_name = model_name;
        this.description = description;
        this.collections = collection;
        this.category = category;
    }
    

    public Furniture(Long id, String model_name, String description, Category category) {
		super();
		this.id = id;
		this.model_name = model_name;
		this.description = description;
		this.category = category;
	}

	public String getModel_name() {
        return model_name;
    }
	
	

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FurnitureInCollection> getFurnitureCollection() {
        return collections;
    }

    public void setFurnitureCollection(Set<FurnitureInCollection> collection) {
        this.collections = collection;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
