package com.ducks.api.ducksapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.ducks.api.ducksapi.model.Duck;
import com.ducks.api.ducksapi.persistence.DuckFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Duck File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class DuckFileDAOTest {
    DuckFileDAO duckFileDAO;
    Duck[] testDucks;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupDuckFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testDucks = new Duck[3];
        testDucks[0] = new Duck(99,"Wi-Fire");
        testDucks[1] = new Duck(100,"Galactic Agent");
        testDucks[2] = new Duck(101,"Ice Gladiator");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the duck array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Duck[].class))
                .thenReturn(testDucks);
        duckFileDAO = new DuckFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetDucks() {
        // Invoke
        Duck[] duckes = duckFileDAO.getDucks();

        // Analyze
        assertEquals(duckes.length,testDucks.length);
        for (int i = 0; i < testDucks.length;++i)
            assertEquals(duckes[i],testDucks[i]);
    }

    @Test
    public void testFindDucks() {
        // Invoke
        Duck[] duckes = duckFileDAO.findDucks("la");

        // Analyze
        assertEquals(duckes.length,2);
        assertEquals(duckes[0],testDucks[1]);
        assertEquals(duckes[1],testDucks[2]);
    }

    @Test
    public void testGetDuck() {
        // Invoke
        Duck duck = duckFileDAO.getDuck(99);

        // Analzye
        assertEquals(duck,testDucks[0]);
    }

    @Test
    public void testDeleteDuck() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> duckFileDAO.deleteDuck(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test duckes array - 1 (because of the delete)
        // Because duckes attribute of DuckFileDAO is package private
        // we can access it directly
        assertEquals(duckFileDAO.ducks.size(),testDucks.length-1);
    }

    @Test
    public void testCreateDuck() {
        // Setup
        Duck duck = new Duck(102,"Wonder-Person");

        // Invoke
        Duck result = assertDoesNotThrow(() -> duckFileDAO.createDuck(duck),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Duck actual = duckFileDAO.getDuck(duck.getId());
        assertEquals(actual.getId(),duck.getId());
        assertEquals(actual.getName(),duck.getName());
    }

    @Test
    public void testUpdateDuck() {
        // Setup
        Duck duck = new Duck(99,"Galactic Agent");

        // Invoke
        Duck result = assertDoesNotThrow(() -> duckFileDAO.updateDuck(duck),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Duck actual = duckFileDAO.getDuck(duck.getId());
        assertEquals(actual,duck);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Duck[].class));

        Duck duck = new Duck(102,"Wi-Fire");

        assertThrows(IOException.class,
                        () -> duckFileDAO.createDuck(duck),
                        "IOException not thrown");
    }

    @Test
    public void testGetDuckNotFound() {
        // Invoke
        Duck duck = duckFileDAO.getDuck(98);

        // Analyze
        assertEquals(duck,null);
    }

    @Test
    public void testDeleteDuckNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> duckFileDAO.deleteDuck(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(duckFileDAO.ducks.size(),testDucks.length);
    }

    @Test
    public void testUpdateDuckNotFound() {
        // Setup
        Duck duck = new Duck(98,"Bolt");

        // Invoke
        Duck result = assertDoesNotThrow(() -> duckFileDAO.updateDuck(duck),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the DuckFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Duck[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new DuckFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
