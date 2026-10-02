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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.dto.DtoMapper;
import org.springframework.samples.petclinic.web.dto.VisitDetails;
import org.springframework.samples.petclinic.web.dto.Visits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@RestController
public class VisitResource extends AbstractResourceController {

    private final ClinicService clinicService;

    @Autowired
    public VisitResource(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping({ "/owners/{ownerId}/pets/{petId}/visits", "/api/visit/owners/{ownerId}/pets/{petId}/visits" })
    @ResponseStatus(HttpStatus.CREATED)
    public VisitDetails create(
            @Valid @RequestBody Visit visit,
            @PathVariable("petId") int petId) {

        clinicService.findPetById(petId).addVisit(visit);
        clinicService.saveVisit(visit);
        return DtoMapper.visitDetails(visit);
    }

    @GetMapping({ "/owners/{ownerId}/pets/{petId}/visits", "/api/visit/owners/{ownerId}/pets/{petId}/visits" })
    public List<VisitDetails> visits(@PathVariable("petId") int petId) {
        return DtoMapper.visits(clinicService.findPetById(petId).getVisits()).items;
    }

    @GetMapping("/api/visit/pets/visits")
    public Visits visitsForPets(@RequestParam("petId") List<Integer> petIds) {
        return DtoMapper.visits(clinicService.findVisitsByPetIds(petIds));
    }
}
