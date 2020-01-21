package com.niemiec.config;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityConstants {
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final long EXPIRATION_TIME = 172_800_000; // 2 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_HEADER = "Authorization";
    public static final String SECRET = "SecretKeyToGenerateJWTs";
    public static final Algorithm ALGORITHM(String secret) {
    	return Algorithm.HMAC256(secret);
    }
}
