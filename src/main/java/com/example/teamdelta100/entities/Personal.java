package com.example.teamdelta100.entities;

import javax.persistence.*;

@Entity
@Table (name = "Personal")
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    // Variabelnamn: "Per" = personal, "Lname" = lastName
    @Column(name = "personalName")
    private String perName;

    @Column(name = "personalLastname")
    private String perLname;

    @Column(name = "personalNickname")
    private String perNickname;

    @Column(name = "address")
    private String perAddress;

    @Column(name = "postal_code")
    private String perPostalCode;

    @Column(name = "city")
    private String perCity;

    @Column(name = "country")
    private String perCountry;

    @Column(name = "email")
    private String perEmail;

    public Personal(int id, String perName, String perLname, String perNickname, String perAddress,
                    String perPostalCode, String perCity, String perCountry, String perEmail) {
        this.Id = id;
        this.perName = perName;
        this.perLname = perLname;
        this.perNickname = perNickname;
        this.perAddress = perAddress;
        this.perPostalCode = perPostalCode;
        this.perCity = perCity;
        this.perCountry = perCountry;
        this.perEmail = perEmail;
    }
    public Personal(){

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerLname() {
        return perLname;
    }

    public void setPerLname(String perLname) {
        this.perLname = perLname;
    }

    public String getPerNickname() {
        return perNickname;
    }

    public void setPerNickname(String perNickname) {
        this.perNickname = perNickname;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPerPostalCode() {
        return perPostalCode;
    }

    public void setPerPostalCode(String perPostalCode) {
        this.perPostalCode = perPostalCode;
    }

    public String getPerCity() {
        return perCity;
    }

    public void setPerCity(String perCity) {
        this.perCity = perCity;
    }

    public String getPerCountry() {
        return perCountry;
    }

    public void setPerCountry(String perCountry) {
        this.perCountry = perCountry;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    @Override
    public String toString() {
        return "Personal: " +
                "id = " + Id +
                ", personalName = " + perName +
                ", personalLastname = " + perLname +
                ", personalNickname = " + perNickname;
    }
}
