package com.niemiec.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niemiec.model.Person;
import static com.niemiec.config.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			Person person = createPersonFromTheParsedJSON(request, Person.class);
			return authenticatePerson(person);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Person createPersonFromTheParsedJSON(HttpServletRequest sourceOfTheJSON, Class<Person> class1) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(sourceOfTheJSON.getInputStream(), class1);
	}

	private Authentication authenticatePerson(Person person) {
		return authenticationManager.authenticate(createUsernamePasswordAuthenticationToken(person));
	}

	private Authentication createUsernamePasswordAuthenticationToken(Person person) {
		return new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword(), new ArrayList<>());
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserDetails principal = (UserDetails) authResult.getPrincipal();
		String token = createToken(principal);
//		response.getOutputStream().print("{\"token\": \"" + token + "\"}");
		response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
	}

	private String createToken(UserDetails principal) {
		return JWT.create()
				.withSubject(principal.getUsername())
				.withExpiresAt(createAnExpirationDate(EXPIRATION_TIME))
				.sign(ALGORITHM(SECRET));
	}

	private Date createAnExpirationDate(long expirationTime) {
		return new Date(System.currentTimeMillis() + expirationTime);
	}
}
