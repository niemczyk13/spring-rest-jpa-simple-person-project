package com.niemiec.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

import static com.niemiec.config.SecurityConstants.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		ifAuthenticationExistSetItToSecurityContextHolder(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = getTokenFromRequest(request);
		if (theTokenIsValid(token)) {
			return getUsernamePasswordAuthenticationToken(token);
		}
		return null;
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		return request.getHeader(TOKEN_HEADER);
	}

	private boolean theTokenIsValid(String token) {
		return token != null && token.startsWith(TOKEN_PREFIX);
	}

	private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token) {
		String username = getUsernameFromToken(token);
		if (username != null) {
			return createUsernamePasswordAuthenticationToken(username);			
		}
		return null;
	}

	private String getUsernameFromToken(String token) {
		return JWT.require(ALGORITHM(SECRET))
				.build()
				.verify(token.replace(TOKEN_PREFIX, ""))
				.getSubject();
	}

	private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
	}

	private void ifAuthenticationExistSetItToSecurityContextHolder(UsernamePasswordAuthenticationToken authentication) {
		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}
}
