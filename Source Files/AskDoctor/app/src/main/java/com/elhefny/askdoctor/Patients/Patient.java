package com.elhefny.askdoctor.Patients;

import java.io.Serializable;

public class Patient implements Serializable {
    private String email, name, country, gender,phone,age;

    public Patient() {
    }

    public Patient(String email, String name, String country, String gender, String phone, String age) {
        this.email = email;
        this.name = name;
        this.country = country;
        this.gender = gender;
        this.phone = phone;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
