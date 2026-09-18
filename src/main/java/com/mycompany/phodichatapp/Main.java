//Filename: Main.java
//Written by: Phodisho Mongwai
//Student number: 10523203
//Date: 18-09-2026
//Purpose: Console application to test the login class.

package com.mycompany.phodichatapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Use Scanner for input a
        Scanner inputDevice = new Scanner(System.in);
        Login loginSystem = new Login();

        System.out.println("=== REGISTRATION ===");
        
        System.out.print("Enter First Name: ");
        String firstName = inputDevice.nextLine();

        System.out.print("Enter Last Name: ");
        String surName = inputDevice.nextLine();

        System.out.print("Enter Username (must contain _ and be <= 5 chars): ");
        String userName = inputDevice.nextLine();

        System.out.print("Enter Password (min 8 chars, 1 capital, 1 number, 1 special): ");
        String password = inputDevice.nextLine();

        System.out.print("Enter Cell Phone Number (+27838968976): ");
        String cellphoneNum = inputDevice.nextLine();

        // Call on the registration method
        String registrationMessage = loginSystem.registerUser(firstName, surName, userName, password, cellphoneNum);
        System.out.println("\n" + registrationMessage);

        // Only proceed to login if registration was successful
        if (registrationMessage.contains("successfully")) {
            System.out.println("\n=== LOGIN ===");
            
            System.out.print("Enter Username: ");
            String loginUserName = inputDevice.nextLine();

            System.out.print("Enter Password: ");
            String loginPassWord = inputDevice.nextLine();

            // Check the login and return status
            boolean isLoggedIn = loginSystem.loginUser(loginUserName, loginPassWord);
            String loginStatus = loginSystem.returnLoginStatus(isLoggedIn);
            
            System.out.println("\n" + loginStatus);
        } else {
            System.out.println("\nRegistration failed. Please fix the errors and try again.");
        }

        inputDevice.close();
    }
}