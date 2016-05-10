package com.example.josip.ratetheplace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Josip on 4.4.2016..
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotel implements Serializable {

    private String author;
    private String name;
    private double latit;
    private double longit;
    private String review;
    private float food;
    private float service;
    private float comfort;
    private float rating;

    public Hotel() {
    }

    public Hotel(String author, String name, double latit, double longit, String review, float food, float service, float comfort, float rating) {
        this.author = author;
        this.name = name;
        this.latit = latit;
        this.longit = longit;
        this.review = review;
        this.food = food;
        this.service = service;
        this.comfort = comfort;
        this.rating = rating;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getService() {
        return service;
    }

    public void setService(float service) {
        this.service = service;
    }

    public float getComfort() {
        return comfort;
    }

    public void setComfort(float comfort) {
        this.comfort = comfort;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getLatit() {
        return latit;
    }

    public void setLatit(double latit) {
        this.latit = latit;
    }

    public double getLongit() {
        return longit;
    }

    public void setLongit(double longit) {
        this.longit = longit;
    }

    public Hotel(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
