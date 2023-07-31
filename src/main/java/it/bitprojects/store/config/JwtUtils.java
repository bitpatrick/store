package it.bitprojects.store.config;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUtils {

	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String generateJwtToken(Authentication authentication) {

		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		String jwtToken = Jwts.builder().setSubject(userPrincipal.getUsername()).signWith(key).compact();

		return jwtToken;
	}

	public String getUsernameFromJwtToken(String token) {

		String usernameFromToken = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();

		return usernameFromToken;
	}

	public boolean validateJwtToken(String authToken) {

		try {

			Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}
