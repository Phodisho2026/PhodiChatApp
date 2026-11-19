package com.mycompany.phodichatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();
    public static int totalMessages = 0;

    
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = String.format("%02d",messageNumber);
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return this.messageID.length() <= 10;
    }

    // Requirement: Return exact message based on rubric
    public String checkRecipientCell() {
        if (this.recipient.startsWith("+") && this.recipient.length() <= 12) { 
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Requirement: 00:0:HITHANKS
    public String createMessageHash() {
        String firstTwo = this.messageID.length() >= 2 ?
                this.messageID.substring(0, 2) :
                this.messageID;
        String[] words = this.messageText.trim().split("\\s+");
        
        if (words.length == 0) {
            return firstTwo + ":" + this.messageNumber + "::";
        }
        
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        
        if (words.length == 1) {
            return (firstTwo + ":" + this.messageNumber + ":" + firstWord + firstWord).toUpperCase();
        }

        return (firstTwo + ":" + this.messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(Scanner input) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Option: ");
        String action = input.nextLine();

        switch (action) {
            case "1":
                sentMessages.add(this);
                totalMessages++;
                return "Message successfully sent.";
            case "2":
                disregardedMessages.add(this);
                return "Press 0 to delete the message.";
            case "3":
                storedMessages.add(this);
                storeMessageToJson();
                return "Message successfully stored.";
            default:
                return "Invalid option. Choose 1, 2, or 3.";
        }
    }

    // Research Requirement: Store to JSON
    public void storeMessageToJson() {
        String json = "{\n" +
                "  \"id\": \"" + this.messageID + "\",\n" +
                "  \"number\": " + this.messageNumber + ",\n" +
                "  \"recipient\": \"" + this.recipient + "\",\n" +
                "  \"message\": \"" + this.messageText + "\",\n" +
                "  \"hash\": \"" + this.messageHash + "\"\n" +
                "}";
        
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json + ",\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    
        // PART 3: REQUIRED METHODS FOR STORED MESSAGES MENU 

    // Method for 2b: Find the longest stored message
    public static String getLongestStoredMessage() {
        String longest = "";
        for (Message msg : storedMessages) {
            if (msg.getMessageText().length() > longest.length()) {
                longest = msg.getMessageText();
            }
        }
        return longest;
    }

    // Method for 2c: Search by Message ID
    public static void searchByMessageID(String searchID) {
        boolean found = false;
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(searchID)) {
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessageText());
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Message ID not found.");
    }

    // Method for 2d: Search by Recipient
    public static void searchByRecipient(String searchRecipient) {
        boolean found = false;
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(searchRecipient)) {
                System.out.println("Message: " + msg.getMessageText());
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for this recipient.");
    }

    // Method for 2e: Delete message by Hash
    public static String deleteMessageByHash(String searchHash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(searchHash)) {
                String deletedText = storedMessages.get(i).getMessageText();
                storedMessages.remove(i); // Deletes it from your ArrayList
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    // Method for 2f: Display Full Report
    public static void displayStoredReport() {
        System.out.println("\n--- Full Stored Messages Report ---");
        for (Message msg : storedMessages) {
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient:    " + msg.getRecipient());
            System.out.println("Message:      " + msg.getMessageText());
            System.out.println("-----------------------------------");
        }
    }
}
