package it.bitprojects.store.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private UserDetailsManager userDetailsManager;
	
	private SecurityContextRepository securityContextRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		String name = null;

		try {

			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
			
			name = defaultOAuth2User.getAttribute("login");

			userDetailsManager.loadUserByUsername(name);

		} catch (UsernameNotFoundException e) {

			// user builder
			UserBuilder userBuilder = User.builder();

			// users
			UserDetails newUser = userBuilder.username(name).password("prova").roles("USER").build();

			// users persistence
			userDetailsManager.createUser(newUser);
			
			// create token user
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(newUser, null, null);
			
			// set security context
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(usernamePasswordAuthenticationToken);
			SecurityContextHolder.setContext(securityContext);
			securityContextRepository.saveContext(securityContext, request, response);
			
			// redirect
			response.sendRedirect("/store/home");
			
		}

	}

}
