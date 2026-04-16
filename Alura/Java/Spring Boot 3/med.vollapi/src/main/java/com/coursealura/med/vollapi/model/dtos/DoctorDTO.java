package com.coursealura.med.vollapi.model.dtos;

import com.coursealura.med.vollapi.model.Adress;
import com.coursealura.med.vollapi.model.enums.Specialty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public class DoctorDTO implements Serializable {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d{4,6}")
    private String crm;
    @NotBlank
    private String phone;
    @NotNull
    private Specialty specialty;
    @NotNull
    @Valid
    private Adress adress;

    public DoctorDTO(){

    }

    public DoctorDTO(String name, String email, String crm, String phone, Specialty specialty, Adress adress) {
        this.name = name;
        this.email = email;
        this.crm = crm;
        this.phone = phone;
        this.specialty = specialty;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", crm='" + crm + '\'' +
                ", specialty=" + specialty +
                ", adress=" + adress +
                '}';
    }
}
