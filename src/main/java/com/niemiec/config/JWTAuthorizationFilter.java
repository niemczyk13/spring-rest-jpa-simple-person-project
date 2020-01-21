package com.niemiec.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

import static com.niemiec.config.SecurityConstants.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		ifAuthenticationExistSetItToSecurityContextHolder(authentication);
		filterChain.doFilter(request, response);
	}

	private void ifAuthenticationExistSetItToSecurityContextHolder(UsernamePasswordAuthenticationToken authentication) {
		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);
		if (theTokenIsValid(token)) {
			return resultOfDownloadingUsernameFromToken(token);
		}
		return null;
	}

	// TODO must add authority/role
	private UsernamePasswordAuthenticationToken resultOfDownloadingUsernameFromToken(String token) {
		String username = getUsernameFromToken(token);
		if (username != null) {
			return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
		}
		return null;
	}

	private String getUsernameFromToken(String token) {
		return JWT.require(ALGORITHM(SECRET))
				.build()
				.verify(token.replace(TOKEN_PREFIX, ""))
				.getSubject();
	}

	private boolean theTokenIsValid(String token) {
		return token != null && token.startsWith(TOKEN_PREFIX);
	}
}
