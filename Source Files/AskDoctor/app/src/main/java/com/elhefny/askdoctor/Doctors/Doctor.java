package com.elhefny.askdoctor.Doctors;

import java.io.Serializable;

public class Doctor implements Serializable {
    private String email, name, department, country, gender, phone, age, yearsOfExperience;

    public Doctor() {
    }

    public Doctor(String email, String name, String department, String country, String gender, String phone, String age, String yearsOfExperience) {
        this.email = email;
        this.name = name;
        this.department = department;
        this.country = country;
        this.gender = gender;
        this.phone = phone;
        this.age = age;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
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

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
