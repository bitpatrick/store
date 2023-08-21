package it.bitprojects.store.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Repository;

import it.bitprojects.store.dto.UserDTO;

@Repository
public class UserRepository extends JdbcUserDetailsManager {

	public UserRepository(DataSource dataSource) {
		super(dataSource);
	}

	public List<UserDTO> getAllUsers() {
		String sql = "SELECT username FROM users"; // Sostituisci "users" con il nome della tua tabella degli utenti
		return getJdbcTemplate().query(sql, (rs, rowNum) -> new UserDTO(rs.getString("username")));
	}


    public Optional<UserDTO> findUserByUsername(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";
        
        return getJdbcTemplate().query(sql, (rs) -> {
            if (rs.next()) {
                String foundUsername = rs.getString("username");
                return Optional.of(new UserDTO(foundUsername));
            } else {
                return Optional.empty();
            }
        }, username);
    }

}
