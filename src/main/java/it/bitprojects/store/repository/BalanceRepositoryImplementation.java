package it.bitprojects.store.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.NumberValue;

import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.bitprojects.store.model.Balance;

@Repository
public class BalanceRepositoryImplementation implements BalanceRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public void initializeBalance(Balance balance, String username) {
		String sql = "SELECT u.token_qty,currency FROM USERS u where u.username = '" + username.toLowerCase() + "'";

		jdbcTemplate.queryForObject(sql, new RowMapper<Balance>() {
			@Override
			public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
				String currencyCode = rs.getString("currency");
				double quantity = rs.getDouble("token_qty");

				// converti i dati in CurrencyUnit e NumberValue
				CurrencyUnit currency = Monetary.getCurrency(currencyCode);
				NumberValue quantityValue = FastMoney.of(quantity, currency).getNumber();

				balance.setCurrency(currency);
				balance.setTokenQty(quantityValue);
				balance.calculateCurrencyQty();

				return null;
			}
		});
	}
}