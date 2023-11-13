package dssd.global.furniture.backend.controllers.dtos.request;

import java.util.List;

public class MaterialRequestDTO {
    private Long collection_id;
    private List<MaterialRequest> materials;
    public static class MaterialRequest {
        private String name;
        private Integer quantity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public Long getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(Long collection_id) {
        this.collection_id = collection_id;
    }

    public List<MaterialRequest> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialRequest> materials) {
        this.materials = materials;
    }
}
/**
 Ejemplo de un JSON de este DTO
{
   "materials": [
     {
       "name": "Plástico",
       "quantity": 4
     }
   ],
   "collection_id": 1
 }
 **/