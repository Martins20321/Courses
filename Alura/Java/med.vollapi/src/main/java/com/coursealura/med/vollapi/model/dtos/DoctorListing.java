package com.coursealura.med.vollapi.model.dtos;

import com.coursealura.med.vollapi.model.Doctor;
import com.coursealura.med.vollapi.model.enums.Specialty;

public record DoctorListing(Long id,
                            String name,
                            String email,
                            String crm,
                            Specialty specialty) {

    public DoctorListing(Doctor doctor){
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }
}
