package dssd.global.furniture.backend.controllers.dtos.api;

public class OffersByApiDTO {
    private Long id;
    private Integer quantity_available;
    private String delivery_date_available;
    private Float price_by_unit;
    private ProviderApiDTO provider;
    private MaterialApiDTO material;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(Integer quantity_available) {
        this.quantity_available = quantity_available;
    }

    public String getDelivery_date_available() {
        return delivery_date_available;
    }

    public void setDelivery_date_available(String delivery_date_available) {
        this.delivery_date_available = delivery_date_available;
    }

    public Float getPrice_by_unit() {
        return price_by_unit;
    }

    public void setPrice_by_unit(Float price_by_unit) {
        this.price_by_unit = price_by_unit;
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
}
