package com.bridgelabz.fundoonotes.user.security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component//autowire
public class TokenProvider {

	private static final String KEY = "VIJAY";

	/**
	 * @param id
	 * @return
	 */
	public String generateToken(String id) {

		return Jwts.builder().setId(id).signWith(SignatureAlgorithm.HS512, KEY).compact();
	}

	/**
	 * @param token
	 * @return
	 */
	public String parseToken(String token) {
		Claims claim = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
		return claim.getId();/// get the email id from the token
	}
}
