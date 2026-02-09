package com.coursealura.med.vollapi.model.dtos;

import com.coursealura.med.vollapi.model.Adress;
import com.coursealura.med.vollapi.model.enums.Specialty;

import java.io.Serializable;

public class DoctorDTO implements Serializable {

    private String name;
    private String email;
    private String phone;
    private String crm;

    private Specialty specialty;
    private Adress adress;

    public DoctorDTO(){

    }

    public DoctorDTO(String name, String email, String phone, String crm, Specialty specialty, Adress adress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.crm = crm;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
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
                ", phone='" + phone + '\'' +
                ", crm='" + crm + '\'' +
                ", specialty=" + specialty +
                ", adress=" + adress +
                '}';
    }
}
