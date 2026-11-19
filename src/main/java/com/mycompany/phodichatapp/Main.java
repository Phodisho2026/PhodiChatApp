//Filename: Main.java
//Written by: Phodisho Mongwai
//Student number: 10523203
//Date: 18-09-2026
//Purpose: Console application to test the login class.

package com.mycompany.phodichatapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputDevice = new Scanner(System.in);
        Login loginSystem = new Login(); 

        // PART 1: REGISTRATION & LOGIN 
        System.out.println("REGISTRATION");
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

        String registrationMessage = loginSystem.registerUser(firstName, surName, userName, password, cellphoneNum);
        System.out.println("\n" + registrationMessage);

        if (registrationMessage.contains("successfully")) {
            System.out.println("\n=== LOGIN ===");
            System.out.print("Enter Username: ");
            String loginUserName = inputDevice.nextLine();
            System.out.print("Enter Password: ");
            String loginPassWord = inputDevice.nextLine();

            boolean isLoggedIn = loginSystem.loginUser(loginUserName, loginPassWord);
            String loginStatus = loginSystem.returnLoginStatus(isLoggedIn);
            System.out.println("\n" + loginStatus);

            if (isLoggedIn) {
                System.out.println("\nWelcome to QuickChat.");
                loadTestData();
                startQuickChat(inputDevice);
            }
        } else {
            System.out.println("\nRegistration failed. Please fix the errors and try again.");
        }
        inputDevice.close();
    } 

    // PART 2: THE MENU LOOP 
    public static void startQuickChat(Scanner inputDevice) {
        boolean running = true;
        int messageCounter = 0;

        while (running) {
            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Choose an option: ");

            String option = inputDevice.nextLine();

            switch (option) {
                case "1":
                    System.out.print("How many messages do you wish to enter? ");
                    int numMessages = 0;
                    try {
                        numMessages = Integer.parseInt(inputDevice.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- MESSAGE " + (i + 1) + " ---");
                        messageCounter++;

                        // Recipient Validation
                        String recipient = "";
                        boolean validRecipient = false;
                        Message tempMessage = null;

                        while (!validRecipient) {
                            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                            recipient = inputDevice.nextLine();
                            
                            tempMessage = new Message(messageCounter, recipient, "");
                            String validationMsg = tempMessage.checkRecipientCell();
                            System.out.println(validationMsg);
                            
                            if (validationMsg.equals("Cell phone number successfully captured.")) {
                                validRecipient = true;
                            }
                        }

                        // Message Validation
                        String messageText = "";
                        boolean validMessage = false;

                        while (!validMessage) {
                            System.out.print("Enter your message (max 250 characters): ");
                            messageText = inputDevice.nextLine();
                            
                            if (messageText.length() <= 250) {
                                validMessage = true;
                                System.out.println("Message sent"); 
                            } else {
                                int excess = messageText.length() - 250;
                                System.out.println("Please enter a message of less than 250 characters. (Exceeded by " + excess + ")");
                            }
                        }

                        
                        // Create the message
                        Message finalMessage = new Message(messageCounter, recipient, messageText);

                        // Show Details
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: " + finalMessage.getMessageID());
                        System.out.println("Message Hash: " + finalMessage.getMessageHash());
                        System.out.println("Recipient: " + finalMessage.getRecipient());
                        System.out.println("Message: " + finalMessage.getMessageText());

                        // Call the method in Message.java to handle the options
                        String actionResult = finalMessage.SentMessage(inputDevice);
                        System.out.println(actionResult);
                    }
                    System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
                    break;

                           case "2":
                System.out.println("Coming Soon.");
                break;

            case "3":
                // NEW: This calls the stored messages menu
                displayStoredMenu(inputDevice); 
                break;

            case "4":
                System.out.println("Exiting QuickChat. Goodbye!");
                running = false;
                break;

            default:
                System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
        }
        }
    }
        // NEW METHOD: The Sub-menu for Stored Messages (Options a-f)
    private static void displayStoredMenu(Scanner inputDevice) {
        boolean subMenu = true;
        while (subMenu) {
            System.out.println("\n--- STORED MESSAGES ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search messages by recipient");
            System.out.println("e. Delete a message by hash");
            System.out.println("f. Display full report");
            System.out.println("g. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            String choice = inputDevice.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("\n--- Stored Recipients ---");
                    for (Message msg : Message.storedMessages) {
                        System.out.println("Sender: User | Recipient: " + msg.getRecipient());
                    }
                    break;
                case "b":
                    System.out.println("\nLongest Message: " + Message.getLongestStoredMessage());
                    break;
                case "c":
                    System.out.print("Enter Message ID: ");
                    Message.searchByMessageID(inputDevice.nextLine());
                    break;
                case "d":
                    System.out.print("Enter Recipient Number: ");
                    Message.searchByRecipient(inputDevice.nextLine());
                    break;
                case "e":
                    System.out.print("Enter Message Hash: ");
                    System.out.println(Message.deleteMessageByHash(inputDevice.nextLine()));
                    break;
                case "f":
                    Message.displayStoredReport();
                    break;
                case "g":
                    subMenu = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // NEW METHOD: Load the 5 Test Messages 
    public static void loadTestData() {
        // Message 1
        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        Message.sentMessages.add(msg1);
        Message.totalMessages++;

        // Message 2
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message.storedMessages.add(msg2);
        Message.totalMessages++;

        // Message 3
        Message msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        Message.disregardedMessages.add(msg3);
        Message.totalMessages++;

        // Message 4
        Message msg4 = new Message(4, "0838884567", "It is dinner time !");
        Message.sentMessages.add(msg4);
        Message.totalMessages++;

        // Message 5
        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        Message.storedMessages.add(msg5);
        Message.totalMessages++;
    }
}