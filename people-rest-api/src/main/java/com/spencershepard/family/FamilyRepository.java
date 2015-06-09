package com.spencershepard.family;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "family", path = "family")
public interface FamilyRepository extends PagingAndSortingRepository<Family, Long> {

    List<Family> findByFatherId(@Param("id") long id);
    
    List<Family> findByMotherId(@Param("id") long id);
}
