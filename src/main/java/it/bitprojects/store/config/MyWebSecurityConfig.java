package it.bitprojects.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@Import(MyWebConfig.class)
@RequiredArgsConstructor
public class MyWebSecurityConfig {

	@Bean
	public UserDetailsService users() {
		UserBuilder users = User.builder();
		UserDetails user = users.username("user").password("password").roles("USER").build();
		UserDetails admin = users.username("admin").password("password").roles("USER", "ADMIN").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(users());
		authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager providerManager() {
		return new ProviderManager(daoAuthenticationProvider());
		
	}

//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

//	@Bean
//	@Order(1)
//	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//
//		http.securityMatcher("/api/**").authorizeHttpRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
//				.httpBasic(Customizer.withDefaults());
//
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    .authenticationManager(providerManager())
	    .csrf(t -> t.disable())
	    .authorizeHttpRequests(
	        
	        t -> 
	        t.requestMatchers("/WEB-INF/views/**").permitAll()
	        .requestMatchers("/h2**").hasRole("ADMIN")
	        .anyRequest().authenticated()
	    
	    )
	    
	    .formLogin(form -> form.loginPage("/login")
	                .defaultSuccessUrl("/home")
	                .permitAll() 
	    )
	    .logout(l -> l.permitAll())
	    .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
	    ;

	    http.headers().frameOptions().disable(); // disabilitare le opzioni di sicurezza per i frame, che sono necessarie per l'H2-console.

	    return http.build();
	}
	
	@Bean
	RememberMeServices rememberMeServices() {
		RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
		TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("myKey", users(), encodingAlgorithm);
		rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
		return rememberMe;
	}

}
