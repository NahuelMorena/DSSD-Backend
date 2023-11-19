package dssd.global.furniture.backend.controllers.dtos;

import java.time.LocalDate;

public class TaskStablishMaterialsDTO {
	private long id;
	private long idCase;
	private String name;
	private long idCollection;
	private LocalDate date_start_manufacture;
    private LocalDate date_end_manufacture;
    private LocalDate estimated_release_date;
    
    
    
	public TaskStablishMaterialsDTO(long id, long idCase,String name, long idCollection, LocalDate date_start_manufacture,
			LocalDate date_end_manufacture, LocalDate estimated_release_date) {
		this.id = id;
		this.idCase = idCase;
		this.name=name;
		this.idCollection = idCollection;
		this.date_start_manufacture = date_start_manufacture;
		this.date_end_manufacture = date_end_manufacture;
		this.estimated_release_date = estimated_release_date;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdCase() {
		return idCase;
	}
	public void setIdCase(long idCase) {
		this.idCase = idCase;
	}
	public long getIdCollection() {
		return idCollection;
	}
	public void setIdCollection(long idCollection) {
		this.idCollection = idCollection;
	}
	public LocalDate getDate_start_manufacture() {
		return date_start_manufacture;
	}
	public void setDate_start_manufacture(LocalDate date_start_manufacture) {
		this.date_start_manufacture = date_start_manufacture;
	}
	public LocalDate getDate_end_manufacture() {
		return date_end_manufacture;
	}
	public void setDate_end_manufacture(LocalDate date_end_manufacture) {
		this.date_end_manufacture = date_end_manufacture;
	}
	public LocalDate getEstimated_release_date() {
		return estimated_release_date;
	}
	public void setEstimated_release_date(LocalDate estimated_release_date) {
		this.estimated_release_date = estimated_release_date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
    
}
