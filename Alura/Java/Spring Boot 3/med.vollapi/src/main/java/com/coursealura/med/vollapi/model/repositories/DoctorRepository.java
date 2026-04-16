package com.coursealura.med.vollapi.model.repositories;

import com.coursealura.med.vollapi.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pageable); //Faz a busca automaticamente
}
