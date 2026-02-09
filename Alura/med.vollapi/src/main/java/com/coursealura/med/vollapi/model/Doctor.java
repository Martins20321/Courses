package com.coursealura.med.vollapi.model;

import com.coursealura.med.vollapi.model.dtos.DoctorDTO;
import com.coursealura.med.vollapi.model.enums.Specialty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String phone;
    private String crm;

    @Enumerated
    private Specialty specialty;

    @Embedded
    private Adress adress;

    public Doctor(DoctorDTO doctorDTO) {
        this.name = doctorDTO.getName();
        this.email = doctorDTO.getEmail();
        this.crm = doctorDTO.getEmail();
        this.specialty = doctorDTO.getSpecialty();
        this.adress = new Adress(doctorDTO.getAdress());
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", crm='" + crm + '\'' +
                ", specialty=" + specialty +
                ", adress=" + adress +
                '}';
    }
}
