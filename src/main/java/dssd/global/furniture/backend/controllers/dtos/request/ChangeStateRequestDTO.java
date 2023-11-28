package dssd.global.furniture.backend.controllers.dtos.request;

public class ChangeStateRequestDTO {
	private Long idCase;
	private String state;
	
	
	public Long getIdCase() {
		return idCase;
	}
	public void setIdCase(Long idCase) {
		this.idCase = idCase;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
