package com.example.statusfirebaseandroidnguyenvantrung;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private  String email;
    private  String password;
    private int normal;
    private int happy;
    private int unhappy;

    public User(String name, String email, String password, int normal, int happy, int unhappy) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.normal = normal;
        this.happy = happy;
        this.unhappy = unhappy;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNormal() {
            return normal;
    }

    public void setNormal(int normal) {
        this.normal=normal;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy=happy;
    }

    public int getUnhappy() {
        return unhappy;
    }

    public void setUnhappy(int unhappy) {
        this.unhappy=unhappy;
    }
}
