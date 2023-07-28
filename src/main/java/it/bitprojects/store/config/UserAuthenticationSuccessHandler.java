package it.bitprojects.store.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import it.bitprojects.store.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private UserService userService;
	

	public UserAuthenticationSuccessHandler(UserService userLocalizationService) {
		super();
		this.userService = userLocalizationService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		userService.initializeUser(request, authentication);
		
		response.sendRedirect("/store/home");

	}

}
