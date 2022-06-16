package me.project.progetto.repositories;

import me.project.progetto.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
