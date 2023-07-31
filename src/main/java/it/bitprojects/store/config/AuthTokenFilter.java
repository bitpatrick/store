package it.bitprojects.store.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

	private JwtUtils jwtUtils;
	
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {
			
			String jwt = parseJwt(request);
			
			/*
			 * check if token is valid
			 */
			if ( jwt != null && jwtUtils.validateJwtToken(jwt) ) {
				
				// recupero username dal token
				String username = jwtUtils.getUsernameFromJwtToken(jwt);
				
				// recupero username dal repository
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				// creo un token 
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				//	authentication.setAuthenticated(true);
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		filterChain.doFilter(request, response);
		
	}
	
	private String parseJwt(HttpServletRequest request) {
		
		String headerAuth = request.getHeader("Authorization");
		if ( StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ") ) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

}
