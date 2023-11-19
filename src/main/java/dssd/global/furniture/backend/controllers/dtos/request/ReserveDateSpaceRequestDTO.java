package dssd.global.furniture.backend.controllers.dtos.request;


import java.util.List;

public class ReserveDateSpaceRequestDTO {
    private Long dateSpace_id;
    private List<ReserveID> reserves;

    public static class ReserveID {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public Long getDateSpace_id() {
        return dateSpace_id;
    }

    public void setDateSpace_id(Long dateSpace_id) {
        this.dateSpace_id = dateSpace_id;
    }

    public List<ReserveID> getReserves() {
        return reserves;
    }

    public void setReserves(List<ReserveID> reserves) {
        this.reserves = reserves;
    }
}
