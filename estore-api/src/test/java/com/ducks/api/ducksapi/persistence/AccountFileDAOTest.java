package com.ducks.api.ducksapi.persistence;

import com.ducks.api.ducksapi.model.Account;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class AccountFileDAOTest {
    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[3];

        testAccounts[0] = new Account(1, "Jeff", "password");
        testAccounts[1] = new Account(2, "Travis", "password");
        testAccounts[2] = new Account(3, "Bob", "password");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the duck array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Account[].class))
                .thenReturn(testAccounts);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt", mockObjectMapper);

    }

    @Test
    public void testGetAccounts() throws IOException {
        // Setup
        setupAccountFileDAO();
        //Invoke
        Account[] accounts = accountFileDAO.getAccounts();
        //Analyze
        assertEquals(accounts.length, testAccounts.length);
        for(int i=0; i<testAccounts.length; i++) {
            assertEquals(accounts[i], testAccounts[i]);
        }
    }

    @Test
    public void testFindAccounts() throws IOException {
        // Setup
        setupAccountFileDAO();
        //Invoke
        Account[] accounts = accountFileDAO.findAccounts("Tra");
        Account[] emptySearch = accountFileDAO.findAccounts("Zuch");
        //Analyze
        assertEquals(accounts.length, 1);
        assertEquals(accounts[0], testAccounts[1]);
        assertEquals(emptySearch.length, 0);
    }

    @Test
    public void testGetAccount() throws IOException {
        // Setup
        setupAccountFileDAO();

        //Invoke
        Account account1 = accountFileDAO.getAccount(1);
        Account account2 = accountFileDAO.getAccount(2);
        Account failResult = accountFileDAO.getAccount(100);
        
        //Analyze
        assertEquals(account1, testAccounts[0]);
        assertEquals(account2, testAccounts[1]);
        assertEquals(failResult, null);
    }

    @Test
    public void testDeleteAccount() throws IOException {
        // Setup
        setupAccountFileDAO();

        //Invoke
        boolean successResult = accountFileDAO.deleteAccount(1);
        boolean failResult = accountFileDAO.deleteAccount(40);
         
        //Analyze
        assertEquals(successResult, true);
        assertEquals(failResult, false);
        assertEquals(accountFileDAO.accounts.size(), testAccounts.length - 1);
    }

    @Test
    public void testCreateAccount() throws IOException {
        // Setup
        setupAccountFileDAO();
        Account successAccount = new Account(4, "Timmy", "password");
        Account failAccount = new Account(5, "Travis", "password");

        // Invoke
        Account successResult = accountFileDAO.createAccount(successAccount);
        Account failResult = accountFileDAO.createAccount(failAccount);

        //Analyze
        Account createdAccount = accountFileDAO.getAccount(successResult.getId());;
        assertEquals(failResult, null);
        assertEquals(successResult.getClass(), Account.class);
        assertEquals(createdAccount.getId(), 4);
        assertEquals(createdAccount.getUsername(), "Timmy");
        assertEquals(createdAccount.getHashedPassword(), "password".hashCode());
        assertEquals(createdAccount.getAdminStatus(), false);
    }

    @Test
    public void testUpdateAccount() throws IOException {
        // Setup
        setupAccountFileDAO();
        Account updatedAccount = new Account(1, "notJeff", "password");
        Account failUpdatedAccount = new Account(50, "notJeff", "password");

        // Invoke
        Account successResult = accountFileDAO.updateAccount(updatedAccount);
        Account failResult = accountFileDAO.updateAccount(failUpdatedAccount);

        // Analyze 
        Account databaseUpdatedAccount = accountFileDAO.getAccount(1);
        assertEquals(failResult, null);
        assertEquals(successResult.getClass(), Account.class);
        assertEquals(databaseUpdatedAccount, updatedAccount);
    }

    @Test
    public void testChangePassword() throws IOException {
        // Setup
        setupAccountFileDAO();
        Account account = accountFileDAO.getAccount(1);
        int accountID = account.getId();
        String newPassword = "password12345";
        int newPasswordHash = newPassword.hashCode();
        // Invoke
        boolean successChange = accountFileDAO.changePassword(accountID, "password", newPassword);
        boolean failChangeWrongID = accountFileDAO.changePassword(999, "password", newPassword);
        boolean failChangeWrongOriginalPassword = accountFileDAO.changePassword(accountID, "password123", newPassword);

        // Analyze
        Account updatedAccount = accountFileDAO.getAccount(accountID);
        assertEquals(successChange, true);
        assertEquals(updatedAccount.getHashedPassword(), newPasswordHash);
        assertEquals(failChangeWrongID, false);
        assertEquals(failChangeWrongOriginalPassword, false);
        

    }

}
