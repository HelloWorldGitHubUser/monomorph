package org.springframework.samples.petclinic.web.dto;

import java.util.List;

public class Visits {

    public final List<VisitDetails> items;

    public Visits(List<VisitDetails> items) {
        this.items = items;
    }

}
