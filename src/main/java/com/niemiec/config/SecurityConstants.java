package com.niemiec.config;

public class SecurityConstants {
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final long EXPIRATION_TIME = 172_800_000; // 2 days
	public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SECRET = "SecretKeyToGenerateJWTs";
}
