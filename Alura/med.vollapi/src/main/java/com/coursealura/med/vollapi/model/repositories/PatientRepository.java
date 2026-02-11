package com.coursealura.med.vollapi.model.repositories;

import com.coursealura.med.vollapi.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
