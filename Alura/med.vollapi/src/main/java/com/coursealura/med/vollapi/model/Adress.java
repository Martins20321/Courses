package com.coursealura.med.vollapi.model;

import java.io.Serializable;

public class Adress implements Serializable {

    private String streetAdress;
    private String number;
    private String additionalInformation;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;

    public Adress(){

    }

    public Adress(String streetAdress, String neighborhood, String city, String state, String postalCode) {
        this.streetAdress = streetAdress;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

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
