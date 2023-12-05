package dssd.global.furniture.backend.controllers.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DatesDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate available_from;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate available_until;

    public LocalDate getAvailable_from() {
        return available_from;
    }

    public void setAvailable_from(LocalDate available_from) {
        this.available_from = available_from;
    }

    public LocalDate getAvailable_until() {
        return available_until;
    }

    public void setAvailable_until(LocalDate available_until) {
        this.available_until = available_until;
    }
}
