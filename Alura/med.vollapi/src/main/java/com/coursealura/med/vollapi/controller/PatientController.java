package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Patient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @PostMapping
    public void insert(@RequestBody Patient patient){
        System.out.println(patient);
    }
}
