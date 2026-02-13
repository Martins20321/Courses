package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Patient;

public record PatientListing(String name,
                             String email,
                             String CPF) {

    public PatientListing(Patient patient){
        this(patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
