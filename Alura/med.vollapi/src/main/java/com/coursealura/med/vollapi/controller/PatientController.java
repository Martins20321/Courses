package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Patient;
import com.coursealura.med.vollapi.model.dtos.PatientDTO;
import com.coursealura.med.vollapi.model.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    public void insert(@RequestBody @Valid PatientDTO patientDTO){
        repository.save(new Patient(patientDTO));
    }
}
