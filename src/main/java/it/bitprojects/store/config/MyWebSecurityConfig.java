package it.bitprojects.store.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@Import(MyWebConfig.class)
@RequiredArgsConstructor
@EnableMethodSecurity
public class MyWebSecurityConfig {
	
	@Autowired
	private UserDetailsManager userDetailsManager;
	
	/**
	 * crea utenti in memoria
	 * 
	 * @return UserDetailsService
	 */
//	@Bean
//	public UserDetailsService users() {
//		
//		// user builder
//		UserBuilder users = User.builder();
//		
//		// users
//		UserDetails user = users.username("user").password("password").roles("USER").build();
//		UserDetails admin = users.username("admin").password("password").roles("USER", "ADMIN").build();
//
//		return new InMemoryUserDetailsManager(user, admin);
//	}

//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(this.githubClientRegistration());
	}

	/**
	 * OAuth2AuthorizedClientService è utilizzato per gestire i client autorizzati
	 * dopo che il processo di autorizzazione è stato completato
	 * 
	 * @param clientRegistrationRepository
	 * @return OAuth2AuthorizedClientService
	 */
	@Bean
	public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
		
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
	}

	/**
	 * ClientRegistrationRepository è utilizzato per memorizzare i dettagli di
	 * registrazione del client
	 * 
	 * @param authorizedClientService
	 * @return OAuth2AuthorizedClientRepository
	 */
	@Bean
	public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
		
		return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
	}

	private ClientRegistration githubClientRegistration() {
		
		return CommonOAuth2Provider.GITHUB.getBuilder("github")
				.clientId("727109d7f1ebfe825d11")
				.clientSecret("458fbf6505ecc362368eca8ec7e8639eef52ce3b")
				.redirectUri("http://localhost:8080/store/login/oauth2")
				.build();
	}

	@Bean
	public AccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsManager);
		authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return authProvider;
	}

	@Bean
	public AuthenticationManager providerManager() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(daoAuthenticationProvider());
		providers.add(new OAuth2LoginAuthenticationProvider(new DefaultAuthorizationCodeTokenResponseClient(), new DefaultOAuth2UserService()));
		return new ProviderManager(providers);
	}

	@Autowired
	private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http	
		.authenticationManager(providerManager())
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(
				
				(authorize) -> authorize
						.dispatcherTypeMatchers(DispatcherType.INCLUDE, DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
						.requestMatchers("/views/**", "/static/**").permitAll()
						.requestMatchers(new RequestMatcher() {
							
							@Override
							public boolean matches(HttpServletRequest request) {
								
								 if ( request.getServletPath().contains("console") ) {
									 return true;
								 }
								 
								return false;
							}
						}).permitAll()
						.requestMatchers("/console/**").permitAll()
						.requestMatchers("/home").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(
						form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/home")
						.permitAll()
				)
				.oauth2Login(o -> o
						.loginProcessingUrl("/login/oauth2")
						.clientRegistrationRepository(clientRegistrationRepository)
						.authorizedClientService(oAuth2AuthorizedClientService)
						.loginPage("/login")
						.defaultSuccessUrl("/home")
						.successHandler(new AuthenticationSuccessHandler() {
							
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException {
								
								try {
									
									userDetailsManager.loadUserByUsername(authentication.getName());
									
								} catch (UsernameNotFoundException e) {

									// user builder
									UserBuilder users = User.builder();
									
									// create user
									UserDetails newUser = users.username(authentication.getName()).build();
									
									// save user
									userDetailsManager.createUser(newUser);
								}
								
							}
						})
				)
				.logout((logout) -> logout.logoutSuccessUrl("/home").permitAll())
				.rememberMe(r -> r.rememberMeServices(rememberMeServices()))
				.headers(headers -> headers.frameOptions().disable()); // disabilitare le opzioni di sicurezza per i
																		// frame, che sono necessarie per l'H2-console.

		return http.build();
	}
	
	@Bean
	RememberMeServices rememberMeServices() {
		RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
		TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("myKey", userDetailsManager, encodingAlgorithm);
		rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
		return rememberMe;
	}

}
