package dssd.global.furniture.backend.controllers.dtos.request;


import java.util.List;

public class ReserveDateSpaceRequestDTO {
    private Long dateSpace_id;
    private List<ReserveID> reserves;

    public static class ReserveID {
        private long reserve_id;

        public long getReserve_id() {
            return reserve_id;
        }

        public void setReserve_id(long reserve_id) {
            this.reserve_id = reserve_id;
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
