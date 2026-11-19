package com.mycompany.phodichatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        String validMessage = "Hi Mike, can you join us for dinner tonight?";
        Message msg = new Message(1, "+27718693002", validMessage);
        assertTrue(msg.getMessageText().length() <= 250);
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message(1, "+27718693002", "Test message");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        Message msg = new Message(1, "08575975889", "Test message");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }
        @Test
    public void testSentMessagesArrayPopulated() {
        // Clear existing data to ensure a clean test
        Message.sentMessages.clear();
        
        // Create the test data exactly as the rubric requires
        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        Message msg4 = new Message(4, "0838884567", "It is dinner time !");
        
        // Add them to the array list
        Message.sentMessages.add(msg1);
        Message.sentMessages.add(msg4);
        
        // Check that the messages were added correctly
        assertEquals("Did you get the cake?", Message.sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time !", Message.sentMessages.get(1).getMessageText());
    }

    @Test
    public void testLongestStoredMessage() {
        Message.storedMessages.clear();
        
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        
        Message.storedMessages.add(msg2);
        Message.storedMessages.add(msg5);
        
        // The system should return the longest one (msg2)
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expectedLongest, Message.getLongestStoredMessage());
    }

    @Test
    public void testDeleteMessageByHash() {
        Message.storedMessages.clear();
        
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message.storedMessages.add(msg2);
        
        // Get the hash of the message we want to delete
        String hashToDelete = msg2.getMessageHash();
        
        // Run your delete method
        String result = Message.deleteMessageByHash(hashToDelete);
        
        // Verify the success message and that the list is now empty
        assertTrue(result.contains("successfully deleted"));
        assertEquals(0, Message.storedMessages.size());
    }
}
