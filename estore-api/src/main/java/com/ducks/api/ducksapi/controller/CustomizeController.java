package com.ducks.api.ducksapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ducks.api.ducksapi.model.Duck;
import com.ducks.api.ducksapi.persistence.DuckDAO;

/**
 * Handles the REST API requests for the Duck resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Beining Zhou
 */

@RestController
@RequestMapping("outfit")
public class CustomizeController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private DuckDAO duckDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param duckDao The {@link DuckDAO Duck Data Access Object} to perform CRUD
     *                operations
     *                <br>
     *                This dependency is injected by the Spring Framework
     */
    public CustomizeController(@Qualifier("customDuckFileDAO") DuckDAO duckDao) {
        this.duckDao = duckDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Duck duck} for the given id
     * 
     * @param id The id used to locate the {@link Duck duck}
     * 
     * @return ResponseEntity with {@link Duck duck} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Duck> getDuck(@PathVariable int id) {
        LOG.log(Level.INFO, "GET /outfit/{0}", id);
        try {
            Duck duck = duckDao.getDuck(id);
            if (duck != null) {
                return new ResponseEntity<>(duck, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Duck ducks}
     * 
     * @return ResponseEntity with array of {@link Duck ducks} objects (may be
     *         empty) and HTTP status of OK<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if no ducks are
     *         found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Duck[]> getDucks() {
        LOG.log(Level.INFO, "GET /outfit");
        try {
            Duck[] ducks = duckDao.getDucks();
            if (ducks != null && ducks.length != 0) {
                return new ResponseEntity<>(ducks, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException ioe) {
            LOG.log(Level.SEVERE, ioe.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Duck duck} with the provided duck object
     * 
     * @param duck - The {@link Duck duck} to create
     * 
     * @return ResponseEntity with created {@link Duck duck} object and HTTP status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Duck duck}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         Will inherently throw a 400 if the duck is invalid
     */
    @PostMapping("")
    public ResponseEntity<Duck> createDuck(@RequestBody Duck duck) {
        LOG.log(Level.INFO, "POST /outfit {0}", duck);
        try {
            Duck newDuck = duckDao.createDuck(duck);
            if (newDuck != null) {
                return new ResponseEntity<>(newDuck, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException ioe) {
            LOG.log(Level.SEVERE, ioe.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Duck duck} with the provided {@linkplain Duck duck}
     * object, if it exists
     * 
     * @param duck The {@link Duck duck} to update
     * 
     * @return ResponseEntity with updated {@link Duck duck} object and HTTP status
     *         of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Duck> updateDuck(@RequestBody Duck duck) {
        LOG.log(Level.INFO, "PUT /outfit {0}", duck);
        try {
            // Makes sure that a duck with this name & different id does not already exist
            Duck foundDuck = duckDao.getDuckByName(duck.getName());
            if (foundDuck != null && foundDuck.getId() != duck.getId()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Duck updateDuck = duckDao.updateDuck(duck);
            if (updateDuck != null) {
                return new ResponseEntity<>(updateDuck, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException ioe) {
            LOG.log(Level.SEVERE, ioe.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Duck duck} with the given id
     * 
     * @param id The id of the {@link Duck duck} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Duck> deleteDuck(@PathVariable int id) {
        LOG.log(Level.INFO, "DELETE /outfit/{0}", id);
        try {
            if (duckDao.deleteDuck(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException ioe) {
            LOG.log(Level.SEVERE, ioe.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}