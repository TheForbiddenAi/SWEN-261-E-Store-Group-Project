package com.ducks.api.ducksapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * A unit test suite for the abstract Account class.
 * Tests both OwnerAccount and UserAccount classes functionality
 * 
 * @author Travis Hill
 */
@Tag("Model-tier")
public class AccountTest {

    @Test
    public void testConstructorPlainPassword() {
        // Setup
        int expectedID = 1;
        String expectedUsername = "admin";
        String plainPassword = "password123";
        int expectedHashPassword = plainPassword.hashCode();
        boolean expectedAdminStatus = false;

        // Invoke
        Account account = new UserAccount(expectedID, expectedUsername, plainPassword);

        //Analyze

        assertEquals(expectedID, account.getId());
        assertEquals(expectedUsername, account.getUsername());
        assertEquals(expectedHashPassword, account.getHashedPassword());
        assertEquals(expectedID, account.getId());
        assertEquals(expectedAdminStatus, account.getAdminStatus());
    }

    @Test
    public void testConstructorHashedPassword() {
        // Setup
        int expectedID = 1;
        String expectedUsername = "admin";
        String plainPassword = "password123";
        int expectedHashPassword = plainPassword.hashCode();
        boolean expectedAdminStatus = false;

        // Invoke
        Account account = new UserAccount(expectedID, expectedUsername, expectedHashPassword);

        //Analyze

        assertEquals(expectedID, account.getId());
        assertEquals(expectedUsername, account.getUsername());
        assertEquals(expectedHashPassword, account.getHashedPassword());
        assertEquals(expectedID, account.getId());
        assertEquals(expectedAdminStatus, account.getAdminStatus());
    }

    @Test
    public void testConstructorAdmin() {
        // Setup
        int expectedID = 0;
        String expectedUsername = "admin";
        String plainPassword = "admin";
        int expectedHashPassword = plainPassword.hashCode();
        boolean expectedAdminStatus = true;

        // Invoke
        Account account = new OwnerAccount();

        //Analyze

        assertEquals(expectedID, account.getId());
        assertEquals(expectedUsername, account.getUsername());
        assertEquals(expectedHashPassword, account.getHashedPassword());
        assertEquals(expectedID, account.getId());
        assertEquals(expectedAdminStatus, account.getAdminStatus());
    }

    @Test
    public void testSetUsername() {
          // Setup
          int expectedID = 1;
          String originalusername = "admin";
          String plainPassword = "password123";

          Account account = new UserAccount(expectedID, originalusername, plainPassword);

          String expectedUsername = "notadmin";
          // Invoke
          account.setUsername(expectedUsername);

          //Analyze
          assertEquals(expectedUsername, account.getUsername());;
    }

    @Test
    public void testSetHashedPassword() {
          // Setup
          int expectedID = 1;
          String expectedUsername = "admin";
          String plainPassword = "password123";

          Account account = new UserAccount(expectedID, expectedUsername, plainPassword);

          String newPassword = "password";
          int expectedHashPassword = newPassword.hashCode();
          // Invoke
          account.setHashedPassword(expectedHashPassword);
          //Analyze
  
          assertEquals(expectedHashPassword, account.getHashedPassword());
    }

    @Test
    public void testSetAdminStatusSuccess() {
          // Setup
          Account admin = new OwnerAccount();
          boolean expectedAdminStatusAfterChange = false;

          // Invoke
          admin.setAdminStatus(expectedAdminStatusAfterChange);
          //Analyze
  
          assertEquals(expectedAdminStatusAfterChange, admin.getAdminStatus());
    }

    @Test
    public void testSetAdminStatusFail() {
          // Setup
          int expectedID = 1;
          String originalusername = "notadmin";
          String plainPassword = "password123";

          Account account = new UserAccount(expectedID, originalusername, plainPassword);

          boolean attemptedStatus = true;
          boolean actualStatus = false;

          // Invoke
          account.setAdminStatus(attemptedStatus);
          //Analyze
  
          assertEquals(actualStatus, account.getAdminStatus());
    }

    @Test
    public void testToStringRegular() {
        // Setup
        int expectedID = 1;
        String expectedUsername = "notadmin";
        String plainPassword = "password123";
        
        Account account = new UserAccount(expectedID, expectedUsername, plainPassword);
        
        String expectedString = expectedUsername + ":" + account.getHashedPassword();
        // Invoke
        String actual_string = account.toString();
        //Analyze
          
        assertEquals(expectedString, actual_string);
    }

    @Test
    public void testToStringAdmin() {
        // Setup
        Account account = new OwnerAccount();
        
        String expectedString = "admin:" + account.getHashedPassword();
        // Invoke
        String actual_string = account.toString();
        //Analyze
          
        assertEquals(expectedString, actual_string);
    }

    @Test
    public void testEqualsUser() {
        // Setup
        int id1 = 1;
        String user1 = "notadmin";
        String pass1 = "password123";
        
        Account account = new UserAccount(id1, user1, pass1);

        int id3 = 1;
        String user3 = "notadmin1";
        String pass3 = "password123";
        
        Account diffUsername = new UserAccount(id3, user3, pass3);

        int id4 = 1;
        String user4 = "notadmin";
        String pass4 = "password1234";
        
        Account diffPassword = new UserAccount(id4, user4, pass4);
    
        // Invoke
        boolean successTest = account.equals(account);
        boolean diffUsernameFail = account.equals(diffUsername);
        boolean diffPasswordFail = account.equals(diffPassword);
        //Analyze
          
        assertEquals(successTest, true);
        assertEquals(diffUsernameFail, false);
        assertEquals(diffPasswordFail, false);
    }
    
}
