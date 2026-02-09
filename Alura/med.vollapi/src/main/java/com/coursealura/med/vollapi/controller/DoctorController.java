package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Adress;
import com.coursealura.med.vollapi.model.Doctor;
import com.coursealura.med.vollapi.model.dtos.DoctorDTO;
import com.coursealura.med.vollapi.model.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    public void insert(@RequestBody DoctorDTO doctorDTO){
        repository.save(new Doctor(doctorDTO));
    }
}
