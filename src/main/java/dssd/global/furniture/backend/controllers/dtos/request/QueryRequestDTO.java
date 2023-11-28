package dssd.global.furniture.backend.controllers.dtos.request;

import java.util.List;

public class QueryRequestDTO {
    private List<Long> ids;

    public QueryRequestDTO(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
