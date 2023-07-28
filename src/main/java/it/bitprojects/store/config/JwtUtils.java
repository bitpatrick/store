package it.bitprojects.store.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUtils {

	private UserDetailsManager userDetailsManager;

	private static final String jwtSecret = "chiavesegreta123";

	private static final Integer jwtExpirationMs = 864000000;

	public String generateJwtToken(Authentication authentication) {

		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		Date now = new Date();

		String jwtToken = Jwts.builder().setSubject(userPrincipal.getUsername())
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		return jwtToken;
	}

	public String getUserNameFromJwtToken(String token) {

		String usernameFromToken = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();

		return usernameFromToken;
	}

	public boolean validateJwtToken(String authToken) {

		try {

			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}
