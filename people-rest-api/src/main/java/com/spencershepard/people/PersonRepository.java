package com.spencershepard.people;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 
 * 
 * @author spencer.shepard
 *
 */
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    List<Person> findByFirstName(@Param("name") String name);

    List<Person> findByLastName(@Param("name") String name);

    List<Person> findByBirthDate(@Param("date") String date);

    List<Person> findByAddress(@Param("address") String address);

}