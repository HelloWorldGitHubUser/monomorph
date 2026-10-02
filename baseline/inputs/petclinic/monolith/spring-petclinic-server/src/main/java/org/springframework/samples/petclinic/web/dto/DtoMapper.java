package org.springframework.samples.petclinic.web.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static OwnerDetails ownerDetails(Owner owner) {
        return new OwnerDetails(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getAddress(),
            owner.getCity(), owner.getTelephone(),
            owner.getPets().stream().map(DtoMapper::petDetails).collect(Collectors.toList()));
    }

    public static PetDetails petDetails(Pet pet) {
        Owner owner = pet.getOwner();
        String ownerName = owner == null ? null : owner.getFirstName() + " " + owner.getLastName();
        return new PetDetails(pet.getId(), pet.getName(), ownerName, pet.getBirthDate(), petTypeDetails(pet.getType()),
            pet.getVisits().stream().map(DtoMapper::visitDetails).collect(Collectors.toList()));
    }

    public static PetTypeDetails petTypeDetails(PetType petType) {
        return new PetTypeDetails(petType.getId(), petType.getName());
    }

    public static VisitDetails visitDetails(Visit visit) {
        Pet pet = visit.getPet();
        Integer petId = pet == null ? null : pet.getId();
        return new VisitDetails(visit.getId(), petId, visit.getDate(), visit.getDescription());
    }

    public static VetDetails vetDetails(Vet vet) {
        return new VetDetails(vet.getId(), vet.getFirstName(), vet.getLastName(),
            vet.getSpecialties().stream()
                .map(specialty -> new SpecialtyDetails(specialty.getId(), specialty.getName()))
                .collect(Collectors.toList()));
    }

    public static Visits visits(List<Visit> visits) {
        return new Visits(visits.stream().map(DtoMapper::visitDetails).collect(Collectors.toList()));
    }

}
