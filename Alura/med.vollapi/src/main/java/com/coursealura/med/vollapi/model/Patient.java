package com.coursealura.med.vollapi.model;

import com.coursealura.med.vollapi.model.dtos.PatientDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    @Embedded
    public Adress adress;

    public Patient(PatientDTO patientDTO) {
        this.name = patientDTO.getName();
        this.email = patientDTO.getEmail();
        this.phone = patientDTO.getPhone();
        this.cpf = patientDTO.getCpf();
        this.adress = new Adress(patientDTO.getAdress());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", adress=" + adress +
                '}';
    }
}
