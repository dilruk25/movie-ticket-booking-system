package com.dilruk.movieticketbooking.model;

public class Person {

    private static int personCount = 1;

    private String name;
    private String email;
    private String password;

    public Person() {
        this.name = "name" + personCount;
        this.email = "name" + personCount + "gmail.com";
        this.password = "1234";
        personCount++;
    }

}
