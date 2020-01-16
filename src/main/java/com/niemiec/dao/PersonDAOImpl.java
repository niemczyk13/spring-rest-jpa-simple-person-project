package com.niemiec.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.niemiec.model.Person;
import com.niemiec.repository.PersonRepository;

@Component
public class PersonDAOImpl implements PersonDAO {
	
	PersonRepository personRepository;
	
	public PersonDAOImpl() {
	}
	
	@Autowired
	public PersonDAOImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}



	@Override
	public List<Person> list() {
		return personRepository.findAll();
	}

	@Override
	public Person get(long id) {
		return personRepository.getOne(id);
	}

	@Override
	public long save(Person person) {
		return personRepository.save(person).getId();
	}

	@Override
	public void update(long id, Person person) {
		person.setId(id);
		personRepository.save(person);
	}

	@Override
	public void delete(long id) {
		personRepository.deleteById(id);
	}

}
