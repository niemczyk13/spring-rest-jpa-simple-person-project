package com.niemiec.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.niemiec.dao.GenericDAO;
import com.niemiec.model.Person;

@Service
public class AuthenticationService implements UserDetailsService {

	private GenericDAO<Person> personDAO;
	
	public AuthenticationService() {
	}
	
	@Autowired
	public AuthenticationService(GenericDAO<Person> personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Person person = personDAO.get(email);
		if (person == null) {
			throw new UsernameNotFoundException(email);
		}
		GrantedAuthority authority = new SimpleGrantedAuthority(person.getRole());
		UserDetails userDetails = (UserDetails) new User(person.getEmail(), person.getPassword(), Arrays.asList(authority));
		return userDetails;
	}

}
