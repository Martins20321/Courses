package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Patient;
import com.coursealura.med.vollapi.model.dtos.PatientDTO;
import com.coursealura.med.vollapi.model.dtos.PatientListing;
import com.coursealura.med.vollapi.model.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public void insert(@RequestBody @Valid PatientDTO patientDTO){
        repository.save(new Patient(patientDTO));
    }

    @GetMapping
    public Page<PatientListing> findAll(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        return repository.findAll(pageable).map(PatientListing::new);
    }
}
