package br.com.fermino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fermino.data.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
