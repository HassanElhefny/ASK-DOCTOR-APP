package com.elhefny.askdoctor.Dedelopers;

import java.io.Serializable;

public class Developer implements Serializable {
    private int image;
    private String linkedIn, Twitter, google, facebook, name, phoneNumber;

    public Developer(int image, String linkedIn, String twitter, String google, String facebook, String name, String phoneNumber) {
        this.image = image;
        this.linkedIn = linkedIn;
        Twitter = twitter;
        this.google = google;
        this.facebook = facebook;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getImage() {
        return image;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getTwitter() {
        return Twitter;
    }

    public String getGoogle() {
        return google;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
