package com.coursealura.med.vollapi.controller;

import com.coursealura.med.vollapi.model.Doctor;
import com.coursealura.med.vollapi.model.dtos.DoctorDTO;
import com.coursealura.med.vollapi.model.dtos.DoctorListing;
import com.coursealura.med.vollapi.model.dtos.DoctorUpdateData;
import com.coursealura.med.vollapi.model.repositories.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public Page<DoctorListing> findAll(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        return repository.findAllByActiveTrue(pageable).map(DoctorListing::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DoctorUpdateData doctorUpdateData){
        var doctor = repository.getReferenceById(doctorUpdateData.id());
        doctor.updateInformation(doctorUpdateData);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        doctor.delete();
    }
}
