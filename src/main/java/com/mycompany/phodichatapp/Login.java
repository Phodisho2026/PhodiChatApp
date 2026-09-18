//Filename:Login.java
//Written by: Phodisho Mongwai
//Student number: 10523203
//Date: 18-09-2026
//Purpose: This class handles registration and login logic.

package com.mycompany.phodichatapp;

public class Login {
    private String userName;
    private String password;
    private String cellphoneNum;
    private String firstName;
    private String surName;

    
    public boolean checkUserName(String userName) {
        return userName.contains("_") && userName.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.[A-Z])(?=.[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
        return password.matches(regex);
    }

    public boolean checkCellPhoneNumber(String cellphoneNum) {
        String regex = "^\\+[0-9]{10,12}$"; 
        return cellphoneNum.matches(regex);
    }

    public String registerUser(String firstName, String surName, String userName, String password, String cellphoneNum) {
        if (!checkUserName(userName)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellphoneNum)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.firstName = firstName;
        this.surName = surName;
        this.userName = userName;
        this.password = password;
        this.cellphoneNum = cellphoneNum;

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    public boolean loginUser(String enteredUserName, String enteredPassword) {
        if (this.userName == null) {
            return false; 
        }
        return enteredUserName.equals(this.userName) && enteredPassword.equals(this.password);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + surName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}