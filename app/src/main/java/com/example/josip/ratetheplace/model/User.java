package com.example.josip.ratetheplace.model;
import java.io.Serializable;

/**
 * Created by Josip on 20.3.2016..
 */
public class User implements Serializable {
    private String username;
    private String uid;
    private String email;
    private String password;
    private Hotel hotel;

    public User() {
    }

    public User(String username, String uid, String email, String password, Hotel hotel) {
        this.username = username;
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}