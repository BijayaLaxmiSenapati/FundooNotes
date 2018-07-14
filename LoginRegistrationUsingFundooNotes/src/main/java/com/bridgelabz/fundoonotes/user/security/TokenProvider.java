package com.bridgelabz.fundoonotes.user.security;

import org.springframework.stereotype.Component;
import com.bridgelabz.fundoonotes.user.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class TokenProvider {
	
	String key = "VIJAY";
	
	public String generateToken(User user) {
		
		
		return Jwts.builder().setId(user.getEmail()).
				signWith(SignatureAlgorithm.HS512, key) .compact();
	}
	
	public String parseToken(String token) {
		Claims claim= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		String emailFromToken=claim.getId();///get the email id from the token
		return emailFromToken;
	}
}
