package com.niemiec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niemiec.model.Person;
import com.niemiec.service.PersonService;

@RestController
public class PersonController {
	
	private PersonService personService;
	
	public PersonController() {
	}
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}


	@GetMapping("/person/get")
	public ResponseEntity<Person> get(long id) {
		Person person = personService.get(id);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	
	@GetMapping("/person/save")
	public ResponseEntity<Person> save(Person person) {
		personService.save(person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	
	@GetMapping("/person/get/all")
	public ResponseEntity<List<Person>> getAll() {
		List<Person> persons = personService.list();
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}
	
	@GetMapping("/person/update")
	public ResponseEntity<Person> updatePerson(long id, Person person) {
		personService.update(id, person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	
	@GetMapping("/person/delete")
	public ResponseEntity<?> delete(long id) {
		personService.delete(id);
		return new ResponseEntity<>(personService.list(), HttpStatus.OK);
	}
}
