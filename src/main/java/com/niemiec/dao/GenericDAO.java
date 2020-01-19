package com.niemiec.dao;

import java.util.List;

import com.niemiec.model.Person;

public interface GenericDAO<T> {
	List<T> list();
	T get(long id);
	long save(T data);
	void update(long id, T data);
	void delete(long id);
	Person get(String email);
}
