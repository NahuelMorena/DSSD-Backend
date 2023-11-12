package dssd.global.furniture.backend.controllers.dtos;

import java.util.List;

public class MaterialRequestDTO {
    private Long collection_id;
    private List<MaterialAPI> materials;
    public static class MaterialAPI {
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

    public List<MaterialAPI> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialAPI> materials) {
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