    package com.coursealura.med.vollapi.model;

    import jakarta.persistence.Column;
    import jakarta.persistence.Embeddable;
    import lombok.*;

    import java.io.Serializable;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Adress implements Serializable {

        @Column(name = "street_Adress")
        private String streetAdress;
        private String number;
        @Column(name = "additional_Information")
        private String additionalInformation;
        private String neighborhood;
        private String city;
        private String state;
        @Column(name = "postal_Code")
        private String postalCode;

        public Adress(Adress adress) {
            this.streetAdress = adress.streetAdress;
            this.neighborhood = adress.neighborhood;
            this.city = adress.city;
            this.state = adress.state;
            this.postalCode = adress.postalCode;
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
