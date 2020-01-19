package com.niemiec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niemiec.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	Person findByEmail(String email);

//	Person getByEmail(String email);

}
