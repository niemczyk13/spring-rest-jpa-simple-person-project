package com.niemiec.service;

import java.util.List;

import com.niemiec.model.Person;

public interface PersonService {
	long save(Person person);
	void update(long id, Person person);
	void delete(long id);
	Person get(long id);
	List<Person> list();
}
