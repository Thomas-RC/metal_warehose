package com.capgemini.programowanie.obiektowe.client;

import java.time.LocalDate;

public class Client {
    private final String firstName;
    private final String lastName;
    private final LocalDate creationDate;
    private boolean isPremium;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = LocalDate.now();
        this.isPremium = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }
}