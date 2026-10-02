package org.springframework.samples.petclinic.web.dto;

public class PetTypeDetails {

    public final Integer id;
    public final String name;

    public PetTypeDetails(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
