package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.dto.DtoMapper;
import org.springframework.samples.petclinic.web.dto.OwnerDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerDetailsResource extends AbstractResourceController {

    private final ClinicService clinicService;

    @Autowired
    public OwnerDetailsResource(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/api/owners/{ownerId}/details")
    public OwnerDetails getOwnerDetails(@PathVariable("ownerId") int ownerId) {
        return DtoMapper.ownerDetails(clinicService.findOwnerById(ownerId));
    }

}
