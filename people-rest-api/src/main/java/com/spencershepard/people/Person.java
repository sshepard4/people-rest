package com.spencershepard.people;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model object for "People" or a person.
 * 
 * @author spencer.shepard
 *
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private long id;
    
    private String firstName;
    
    private String lastName;
    
    private String birthDate;
    
    private String address;
    
    private String gender;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public Person setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public Person setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public Person setGender(String gender) {
        this.gender = gender;
        return this;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
}
