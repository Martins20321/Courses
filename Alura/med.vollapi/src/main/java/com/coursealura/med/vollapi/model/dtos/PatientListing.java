package com.coursealura.med.vollapi.model.dtos;

import com.coursealura.med.vollapi.model.Patient;

public record PatientListing(Long id,
                             String name,
                             String email,
                             String CPF) {

    public PatientListing(Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
