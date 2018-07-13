package com.bridgelabz.fundoonotes.user.security;

import java.security.Key;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.user.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class TokenProvider {
	
	Key key = MacProvider.generateKey();
	
	public String generateToken(User user) {
		
		
		return Jwts.builder().setSubject(user.getEmail()).
				signWith(SignatureAlgorithm.HS512, key) .compact();
	}
	
	public String parseToken(String token) {
		Claims claim= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		String emailFromToken=claim.getSubject();///get the email id from the token
		return emailFromToken;
	}
}
