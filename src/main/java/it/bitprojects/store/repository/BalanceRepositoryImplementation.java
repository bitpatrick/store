package it.bitprojects.store.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.bitprojects.store.model.Balance;

@Repository
public class BalanceRepositoryImplementation implements BalanceRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Balance getBalance(String username) {
		
		return null;
	}
	
}