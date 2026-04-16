package com.coursealura.med.vollapi.model.repositories;

import com.coursealura.med.vollapi.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByActiveTrue(Pageable pageable); //Query method para retornar apenas pacientes ativos.
}
