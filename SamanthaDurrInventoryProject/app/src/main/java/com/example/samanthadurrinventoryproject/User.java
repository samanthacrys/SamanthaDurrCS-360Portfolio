/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */
package com.example.samanthadurrinventoryproject;

public class User {

    //Variables for the CompanyID, username, and password.
    int CompanyId;
    String username;
    String password;

    //Sets the company id, name, and password.
    public User(String companyId, String name, String pass) {

        super();
    }

    //Uses the variables received in and sets them to this.
    public User(int ii, String username, String password) {
        super();

        this.CompanyId = ii;
        this.username = username;
        this.password = password;
    }

    //Sets the password and username.
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Gets the company ID.
    public int getCompanyId() {

        return CompanyId;
    }

    //Sets the company id.
    public void  setCompanyId(int i) {

        this.CompanyId = CompanyId;
    }

    //Gets the username.
    public String getUsername() {

        return username;
    }

    //Sets the username.
    public void setUsername(String username) {

        this.username = username;
    }

    //gets the password.
    public String getPassword() {

        return password;
    }

    //Sets the password.
    public void setPassword(String password) {

        this.password = password;
    }
}
