package com.project.jose.account;

import jakarta.persistence.Entity;

@Entity(name = "teachers")
public class Teacher extends User {


    public Teacher(){

    }
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
