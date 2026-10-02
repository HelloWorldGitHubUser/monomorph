package org.springframework.samples.petclinic.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VisitDetails {

    public final Integer id;
    public final Integer petId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public final Date date;
    public final String description;

    public VisitDetails(Integer id, Integer petId, Date date, String description) {
        this.id = id;
        this.petId = petId;
        this.date = date;
        this.description = description;
    }

}
