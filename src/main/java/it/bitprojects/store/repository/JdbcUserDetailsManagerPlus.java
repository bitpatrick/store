package it.bitprojects.store.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

public class JdbcUserDetailsManagerPlus extends JdbcUserDetailsManager {

	public JdbcUserDetailsManagerPlus(DataSource dataSource) {
		super(dataSource);
	}

	public List<UserDetails> getAllUsers() {

		// TODO scrivere sql per recuperare gli utenti
//		getJdbcTemplate()

		return null;
	}

}
