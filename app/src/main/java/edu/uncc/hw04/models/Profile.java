package edu.uncc.hw04.models;

import java.io.Serializable;

public class Profile implements Serializable {
    double weight;
    String gender;

    public Profile(double weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "weight=" + weight +
                ", gender='" + gender + '\'' +
                '}';
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
