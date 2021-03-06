package com.niemiec.dao;

import java.util.List;

public interface GenericDAO<T> {
	List<T> list();
	T get(long id);
	T get(String id);
	long save(T data);
	void update(long id, T data);
	void delete(long id);
}
