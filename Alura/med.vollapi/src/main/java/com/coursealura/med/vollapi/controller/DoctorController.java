package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Doctor;
import com.coursealura.med.vollapi.model.dtos.DoctorDTO;
import com.coursealura.med.vollapi.model.dtos.DoctorListing;
import com.coursealura.med.vollapi.model.repositories.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void insert(@RequestBody @Valid DoctorDTO doctorDTO){
        repository.save(new Doctor(doctorDTO));
    }

    @GetMapping
    public Page<DoctorListing> findAll(Pageable pageable){
        return repository.findAll(pageable).map(DoctorListing::new);
    }
}
