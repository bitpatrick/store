package it.bitprojects.store.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
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
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
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
	
	@Autowired
	private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	
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
	 * registrazione del client, ovvero, dell'app 
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
		
		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
		encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
		encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
		encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
		encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256",
				new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
		encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
		encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		
		MyCustomPasswordEncoder myCustomPasswordEncoder = new MyCustomPasswordEncoder(encodingId, encoders);
		
		authProvider.setPasswordEncoder(myCustomPasswordEncoder);
		
		return authProvider;
	}

	@Bean
	public AuthenticationManager providerManager() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(daoAuthenticationProvider());
		providers.add(new OAuth2LoginAuthenticationProvider(new DefaultAuthorizationCodeTokenResponseClient(), new DefaultOAuth2UserService()));
		return new ProviderManager(providers);
	}
	
	@Bean
	public SecurityContextRepository httpSecurityContext() {
		return new HttpSessionSecurityContextRepository();
	}
	
	@Bean
	public AuthenticationSuccessHandler oAuthAuthenticationSuccessHandler() {
		return new OAuthAuthenticationSuccessHandler(userDetailsManager, httpSecurityContext());
	}

	@Order(Ordered.LOWEST_PRECEDENCE)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http	
		.authenticationManager(providerManager())
		.csrf(csrf -> csrf.disable())
		.requestCache(rc -> rc.disable())
		.securityMatcher("/**")
		.securityContext(sc -> sc
				.requireExplicitSave(true)
				.securityContextRepository(
						httpSecurityContext()
					)
		)
		.authorizeHttpRequests(
				
				(authorize) -> authorize
						.dispatcherTypeMatchers(DispatcherType.INCLUDE, DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
						.requestMatchers("/login**", "/views/**", "/static/**").permitAll()
						.requestMatchers(new RequestMatcher() {
							
							@Override
							public boolean matches(HttpServletRequest request) {
								
								 if ( request.getServletPath().contains("console") ) {
									 return true;
								 }
								 
								return false;
							}
						}).permitAll()
						.requestMatchers("/home").permitAll()
						.anyRequest().authenticated()
				)
		
				// aggiunge automaticamente il filtro UsernamePasswordAuthenticationFilter
				.formLogin(
						form -> 
						form
						.loginPage("/login")
						.defaultSuccessUrl("/home")
//						.failureUrl("/login?error=credenziali%20non%20corrette")
						.failureHandler(new AuthenticationFailureHandler() {
							
							@Override
							public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
								response.sendRedirect("/store/login?error="+exception.getMessage());
							}
						})
						.permitAll()
					)
				
				.oauth2Login(o -> o
						
						.loginProcessingUrl("/login/oauth2") // che path deve avere la request per essere gestita dal filtro ?
						.clientRegistrationRepository(clientRegistrationRepository) // dove sono i client registrati ( le app ) 
						.authorizedClientService(oAuth2AuthorizedClientService) // recupera le APP che sono autorizzate da github
						.loginPage("/login") // imposto la pagina di login
						.successHandler(oAuthAuthenticationSuccessHandler())
//						.defaultSuccessUrl("/home") // va in conflitto con oAuthAuthenticationSuccessHandler
						
						)
				
				.logout((logout) -> 
				logout.logoutSuccessUrl("/home")
				)
				.rememberMe(r -> r.rememberMeServices(rememberMeServices()))
				.headers(headers -> headers.frameOptions().disable()); // disabilitare le opzioni di sicurezza per i frame, che sono necessarie per l'H2-console.

		return http.build();
	}
	
	@Bean
	public JwtUtils jwtUtils() {
		return new JwtUtils();
	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		
		http
			.cors(cors -> cors.disable())
			.csrf(csrf -> csrf.disable())
			.securityMatcher("/api/**")
			.authorizeHttpRequests(r -> r
					.requestMatchers("/api/login**").permitAll()
					.anyRequest().authenticated()
					)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					
					)
			.exceptionHandling(e -> e
					.authenticationEntryPoint(new AuthyEntryPointUnauthorizedJwt())
					)
			
			.addFilterBefore(new AuthTokenFilter(jwtUtils(), userDetailsManager), UsernamePasswordAuthenticationFilter.class)
			;
		
		
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
