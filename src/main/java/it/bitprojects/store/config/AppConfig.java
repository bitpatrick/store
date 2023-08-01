package it.bitprojects.store.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // abilita l'utilizzo dell'annotazione @Transactional
@ComponentScan(basePackages = { "it.bitprojects.store.model", "it.bitprojects.store.repository",
		"it.bitprojects.store.service", "it.bitprojects.store.listener" })
public class AppConfig {

	@Bean
	public UserDetailsManager userDetailsManager() {

		// user builder
		UserBuilder users = User.builder();

		// users
		UserDetails user = users.username("user").password("password").roles("USER").build();
		UserDetails admin = users.username("admin").password("password").roles("USER", "ADMIN").build();

		UserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource());

		// users persistence
		userDetailsManager.createUser(user);
		userDetailsManager.createUser(admin);

		return userDetailsManager;
	}

	/**
	 * creazione database in memoria
	 */
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).addScript("classpath:jdbc/schema.sql")
				.addScript("classpath:jdbc/test-data.sql").build();
	}

	/**
	 * QUERY MANAGER
	 * 
	 * @param dataSource
	 * @return JdbcTemplate
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * TRANSACTION MANAGER
	 * 
	 * abilita la possibilità di utilizzare le transazioni tramite xml
	 * 
	 * per abilitare le transazioni tramite @Transactional devo aggiungere
	 * l'annotazione @EnableTransactionManagement alla mia classe @Configuration
	 * 
	 * @param dataSource
	 * @return PlatformTransactionManager
	 */
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
