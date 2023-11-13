package dssd.global.furniture.backend.controllers.dtos.request;

import java.util.Collection;
import java.util.List;

public class OffersToReserveDTO {

    private List<Offer> offers;
    public static class Offer {
        private Long idProviderOfferMaterial;
        private Integer quantity;

        public Long getIdProviderOfferMaterial() {
            return idProviderOfferMaterial;
        }

        public void setIdProviderOfferMaterial(Long idProviderOfferMaterial) {
            this.idProviderOfferMaterial = idProviderOfferMaterial;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffer(List<Offer> offers) {
        this.offers = offers;
    }
}
