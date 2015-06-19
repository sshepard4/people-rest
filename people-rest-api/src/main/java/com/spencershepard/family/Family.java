package com.spencershepard.family;

import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.spencershepard.people.Person;

/**
 * Model object for a family consisting of father, mother, and children.
 * 
 * @author spencer.shepard
 *
 */
@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private long id;

    @Nullable
    @ManyToOne
    private Person father;
    
    @Nullable
    @ManyToOne
    private Person mother;
    
    @OneToMany
    private Set<Person> children;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return father
     */
    public Person getFather() {
        return father;
    }

    /**
     * @param father the father to set
     */
    public Family setFather(Person father) {
        this.father = father;        
        return this;
    }

    /**
     * @return the mother
     */
    public Person getMother() {
        return mother;
    }

    /**
     * @param mother the mother to set
     */
    public Family setMother(Person mother) {
        this.mother = mother;
        return this;
    }

    /**
     * @return the children
     */
    public Set<Person> getChildren() {
        return children;
    }

    /**
     * @param children children in the family
     */
    public Family setChildren(Set<Person> children) {
        this.children = children;
        return this;
    }
}
