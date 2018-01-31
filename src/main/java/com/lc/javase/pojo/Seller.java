package com.lc.javase.pojo;

import java.time.LocalDate;

public class Seller {
    public enum Sex{
        MALE,FEMALE
    }

    private String name;
    private LocalDate birthday = LocalDate.now();
    private Sex gender;
    private String emailAddress;

    public Seller() {}

    public Seller(String name) {
        this.name = name;
    }

    public Seller(String name, Sex gender) {
        this.name = name;
        this.gender = gender;
    }

    public Seller(String name, LocalDate birthday, Sex gender, String emailAddress) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int compareByName(Seller a, Seller b){
        return a.name.compareTo(b.name);
    }

    public static int compareByBirthday(Seller a, Seller b){
        return a.birthday.compareTo(b.birthday);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
