package it.bitprojects.store.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import it.bitprojects.store.service.UserLocalizationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserlocalizationAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private UserLocalizationService userLocalizationService;

	public UserlocalizationAuthenticationSuccessHandler(UserLocalizationService userLocalizationService) {
		super();
		this.userLocalizationService = userLocalizationService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		userLocalizationService.updateUserLocalization(request, authentication);
		
		response.sendRedirect("/store/home");

	}

}
