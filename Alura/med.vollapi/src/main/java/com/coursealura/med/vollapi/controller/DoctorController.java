package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Doctor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @PostMapping
    public void insert(@RequestBody Doctor doctor){
        System.out.println(doctor);
    }
}
