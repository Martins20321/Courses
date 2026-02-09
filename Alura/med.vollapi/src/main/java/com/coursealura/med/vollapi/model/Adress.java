package com.coursealura.med.vollapi.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adress implements Serializable {

    private String streetAdress;
    private String number;
    private String additionalInformation;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;

    @Override
    public String toString() {
        return "Adress{" +
                "streetAdress='" + streetAdress + '\'' +
                ", number='" + number + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
