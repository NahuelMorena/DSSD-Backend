package dssd.global.furniture.backend.controllers.dtos.request;

public class IDsRequestDTO {
    private Long collection_id;
    private Long process_instance_id;

    public Long getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(Long collection_id) {
        this.collection_id = collection_id;
    }

    public Long getProcess_instance_id() {
        return process_instance_id;
    }

    public void setProcess_instance_id(Long process_instance_id) {
        this.process_instance_id = process_instance_id;
    }
}
