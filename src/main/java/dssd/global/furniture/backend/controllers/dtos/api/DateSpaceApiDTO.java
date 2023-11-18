package dssd.global.furniture.backend.controllers.dtos.api;

import java.util.List;

public class DateSpaceApiDTO {
    private Long id;
    private String available_from;
    private String available_until;
    private Boolean reserved;
    private List<ReserveByApiDTO> reserves;
    private ManufacturingSpace manufacturingSpace;
    public static class ManufacturingSpace {
        private Long id;
        private String name;
        private String direction;
        private Float price_per_day;
        private String phone;
        private String email;

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

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public Float getPrice_per_day() {
            return price_per_day;
        }

        public void setPrice_per_day(Float price_per_day) {
            this.price_per_day = price_per_day;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvailable_from() {
        return available_from;
    }

    public void setAvailable_from(String available_from) {
        this.available_from = available_from;
    }

    public String getAvailable_until() {
        return available_until;
    }

    public void setAvailable_until(String available_until) {
        this.available_until = available_until;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public List<ReserveByApiDTO> getReserves() {
        return reserves;
    }

    public void setReserves(List<ReserveByApiDTO> reserves) {
        this.reserves = reserves;
    }

    public ManufacturingSpace getManufacturingSpace() {
        return manufacturingSpace;
    }

    public void setManufacturingSpace(ManufacturingSpace manufacturingSpace) {
        this.manufacturingSpace = manufacturingSpace;
    }
}
