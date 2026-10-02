/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.dto.DtoMapper;
import org.springframework.samples.petclinic.web.dto.PetDetails;
import org.springframework.samples.petclinic.web.dto.PetTypeDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@RestController
public class PetResource extends AbstractResourceController {

    private final ClinicService clinicService;

    @Autowired
    public PetResource(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping({ "/petTypes", "/api/customer/petTypes" })
    List<PetTypeDetails> getPetTypes() {
        return clinicService.findPetTypes().stream().map(DtoMapper::petTypeDetails).collect(Collectors.toList());
    }

    @PostMapping({ "/owners/{ownerId}/pets", "/api/customer/owners/{ownerId}/pets" })
    @ResponseStatus(HttpStatus.CREATED)
    public PetDetails processCreationForm(
            @RequestBody PetRequest petRequest,
            @PathVariable("ownerId") int ownerId) {

        Pet pet = new Pet();
        Owner owner = this.clinicService.findOwnerById(ownerId);
        owner.addPet(pet);

        save(pet, petRequest);
        return DtoMapper.petDetails(pet);
    }

    @PutMapping({ "/owners/{ownerId}/pets/{petId}", "/api/customer/owners/{ownerId}/pets/{petId}" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@RequestBody PetRequest petRequest) {
        save(clinicService.findPetById(petRequest.getId()), petRequest);
    }

    private void save(Pet pet, PetRequest petRequest) {

        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());

        for (PetType petType : clinicService.findPetTypes()) {
            if (petType.getId() == petRequest.getTypeId()) {
                pet.setType(petType);
            }
        }

        clinicService.savePet(pet);
    }

    @GetMapping({ "/owners/*/pets/{petId}", "/api/customer/owners/*/pets/{petId}" })
    public PetDetails findPet(@PathVariable("petId") int petId) {
        return DtoMapper.petDetails(this.clinicService.findPetById(petId));
    }

    static class PetRequest {
        int id;
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate;
        @Size(min = 1)
        String name;
        int typeId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }

}
