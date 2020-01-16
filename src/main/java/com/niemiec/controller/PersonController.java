package com.niemiec.controller;

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
	public ResponseEntity<Person> get(Long id) {
		Person person = personService.get(id);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	
	@GetMapping("/person/add")
	public ResponseEntity<Person> add(Person person) {
		personService.save(person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
}
