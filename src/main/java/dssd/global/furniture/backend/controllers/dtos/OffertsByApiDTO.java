package dssd.global.furniture.backend.controllers.dtos;

public class OffertsByApiDTO {
    private Long id;
    private Integer quantity_available;
    private String delivery_date_available;
    private Float price_by_unit;
    private ProviderAPI provider;
    private MaterialAPI material;

    public static class ProviderAPI {
        private Long id;
        private String role;
        private String name;
        private String phone;
        private String email;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class MaterialAPI {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

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

    public ProviderAPI getProvider() {
        return provider;
    }

    public void setProvider(ProviderAPI provider) {
        this.provider = provider;
    }

    public MaterialAPI getMaterial() {
        return material;
    }

    public void setMaterial(MaterialAPI material) {
        this.material = material;
    }
}
