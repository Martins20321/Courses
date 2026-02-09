package com.coursealura.med.vollapi.model.dtos;

import com.coursealura.med.vollapi.model.Adress;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

public class PatientDTO implements Serializable {

    private String name;
    private String email;
    private String phone;
    private String cpf;

    public Adress adress;
    public PatientDTO(){

    }

    public PatientDTO(String name, String email, String phone, String cpf, Adress adress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
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
