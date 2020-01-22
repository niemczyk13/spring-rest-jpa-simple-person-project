package com.niemiec.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.niemiec.dao.PersonDAO;
import com.niemiec.model.Person;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private PersonDAO personDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person person = personDAO.get(username);
		if (person == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return createUserDetails(username, person);
	}

	private UserDetails createUserDetails(String username, Person person) {
		UserBuilder builder = null;
		builder = User.withUsername(username);
		builder.password(person.getPassword());
		builder.roles(person.getRole());
		
		return builder.build();
	}

}
