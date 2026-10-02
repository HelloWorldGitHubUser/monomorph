package org.springframework.samples.petclinic.web.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PetDetails {

    public final Integer id;
    public final String name;
    public final String owner;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public final Date birthDate;
    public final PetTypeDetails type;
    public final List<VisitDetails> visits;

    public PetDetails(Integer id, String name, String owner, Date birthDate, PetTypeDetails type,
            List<VisitDetails> visits) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.birthDate = birthDate;
        this.type = type;
        this.visits = visits;
    }

}
