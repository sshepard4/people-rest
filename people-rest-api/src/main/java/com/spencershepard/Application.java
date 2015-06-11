package com.spencershepard;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spencershepard.family.Family;
import com.spencershepard.family.FamilyRepository;
import com.spencershepard.people.Gender;
import com.spencershepard.people.Person;
import com.spencershepard.people.PersonRepository;

@SpringBootApplication
public class Application {

    /**
     * Initialize the repository
     * 
     * @param personRepository
     *            Person repository
     * @param familyRepository
     *            Family repository
     * @return command line runner
     */
    @Bean
    CommandLineRunner init(PersonRepository personRepository, FamilyRepository familyRepository) {
        return (evt) -> {
            Person father = personRepository.save(new Person().setFirstName("Al").setLastName("Bundy")
                    .setBirthDate(LocalDate.of(1960, 1, 2)).setAddress("123 Sesame St").setGender(Gender.MALE));
            Person mother = personRepository.save(new Person().setFirstName("Peg").setLastName("Bundy")
                    .setBirthDate(LocalDate.of(1981, 2, 28)).setAddress("123 Sesame St").setGender(Gender.FEMALE));
            Set<Person> children = new HashSet<>();
            children.add(personRepository.save(new Person().setFirstName("Christina").setLastName("Applegate")
                    .setBirthDate(LocalDate.of(2001, 3, 31)).setAddress("123 Sesame St").setGender(Gender.FEMALE)));
            children.add(personRepository.save(new Person().setFirstName("Bud").setLastName("Williams")
                    .setBirthDate(LocalDate.of(2010, 4, 30)).setAddress("123 Sesame St").setGender(Gender.MALE)));
            familyRepository.save(new Family().setFather(father).setMother(mother).setChildren(children));
        };
    }
    
    /**
     * 
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}