package dssd.global.furniture.backend.controllers.dtos.apiBonita;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ArchivedCases {
	
	private long rootCaseId; 
	
	private long id;
	
	private String state;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime start;
	
    private long startedBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime end_date;
    
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime last_update_date;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime archivedDate;
    
	public LocalDateTime getArchivedDate() {
		return archivedDate;
	}
	public void setArchivedDate(LocalDateTime archivedDate) {
		this.archivedDate = archivedDate;
	}
	public long getRootCaseId() {
		return rootCaseId;
	}
	public void setRootCaseId(long rootCaseId) {
		this.rootCaseId = rootCaseId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public long getStartedBy() {
		return startedBy;
	}
	public void setStartedBy(long startedBy) {
		this.startedBy = startedBy;
	}
	public LocalDateTime getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}

	public LocalDateTime getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(LocalDateTime last_update_date) {
		this.last_update_date = last_update_date;
	}
	
    
    


}
