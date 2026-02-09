package com.coursealura.med.vollapi.model.repositories;

import com.coursealura.med.vollapi.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
