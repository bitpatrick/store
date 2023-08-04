package it.bitprojects.store.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.bitprojects.store.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDetailsImpl implements UserDetails {
	
	private Integer id;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private boolean accountNonLocked = true;
	private boolean accountNonExpired = false;
	private boolean credentialsNonExpired = true;
	private LocalDate expirationTime;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Integer id, String username, String email, String password, boolean accountNonExpired,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.accountNonExpired = accountNonExpired;
		this.authorities = authorities;
	}

	@Override
	public boolean isEnabled() {
		
		if ( this.accountNonExpired && this.accountNonLocked ) {
			
			return true;
		}
		
		return false;
	}
	
	public static UserDetailsImpl build(User user) {
		
		List<? extends GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().name() )).toList();
		
		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getActive(), authorities);
	}

}
