package com.bridgelabz.fundoonotes.user.security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider {

	String key = "VIJAY";

	public String generateToken(String Id) {

		return Jwts.builder().setId(Id).signWith(SignatureAlgorithm.HS512, key).compact();
	}

	public String parseToken(String token) {
		Claims claim = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claim.getId();/// get the email id from the token
	}
}
