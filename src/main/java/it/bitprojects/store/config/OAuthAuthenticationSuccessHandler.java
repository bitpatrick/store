package it.bitprojects.store.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private UserDetailsManager userDetailsManager;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		String name = null;

		try {

			name = authentication.getName();
			System.out.println("authentication.getName(): " + name);

			userDetailsManager.loadUserByUsername(name);

		} catch (UsernameNotFoundException e) {

			// user builder
			UserBuilder users = User.builder();

			// create user
			UserDetails newUser = users.username(name).build();

			// save user
			userDetailsManager.createUser(newUser);
		}

	}

}
