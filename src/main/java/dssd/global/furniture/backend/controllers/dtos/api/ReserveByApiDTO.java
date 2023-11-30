package dssd.global.furniture.backend.controllers.dtos.api;

public class ReserveByApiDTO {
    private Long id;
    private Integer quantity;
    private String delivery_date;
    private Integer number_of_rescheduling;
    private ProviderApiDTO provider;
    private MaterialApiDTO material;
    private DateSpaceApiDTO dateSpaces;
    private Long collectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public Integer getNumber_of_rescheduling() {
        return number_of_rescheduling;
    }

    public void setNumber_of_rescheduling(Integer number_of_rescheduling) {
        this.number_of_rescheduling = number_of_rescheduling;
    }

    public ProviderApiDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderApiDTO provider) {
        this.provider = provider;
    }

    public MaterialApiDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialApiDTO material) {
        this.material = material;
    }

	public DateSpaceApiDTO getDateSpaces() {
		return dateSpaces;
	}

	public void setDateSpaces(DateSpaceApiDTO dateSpaces) {
		this.dateSpaces = dateSpaces;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}
	
	
    
    
}
