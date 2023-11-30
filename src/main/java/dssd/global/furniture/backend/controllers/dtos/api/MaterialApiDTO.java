package dssd.global.furniture.backend.controllers.dtos.api;

public class MaterialApiDTO {
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

	public MaterialApiDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public MaterialApiDTO() {
	}
    
    
}
