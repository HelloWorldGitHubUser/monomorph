package org.springframework.samples.petclinic.web.dto;

import java.util.List;

public class OwnerDetails {

    public final Integer id;
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String city;
    public final String telephone;
    public final List<PetDetails> pets;

    public OwnerDetails(Integer id, String firstName, String lastName, String address, String city, String telephone,
            List<PetDetails> pets) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

}
