package com.niemiec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.niemiec.dao.GenericDAO;
import com.niemiec.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	private GenericDAO<Person> personDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public PersonServiceImpl() {
	}

	@Autowired
	public PersonServiceImpl(GenericDAO<Person> personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public long save(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		return personDAO.save(person);
	}

	@Override
	public void update(long id, Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		personDAO.update(id, person);
	}

	@Override
	public void delete(long id) {
		personDAO.delete(id);
	}

	@Override
	public Person get(long id) {
		return personDAO.get(id);
	}

	@Override
	public List<Person> list() {
		return personDAO.list();
	}

}
