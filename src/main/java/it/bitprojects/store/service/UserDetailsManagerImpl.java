package it.bitprojects.store.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import it.bitprojects.store.entity.Role;
import it.bitprojects.store.entity.Role.RoleType;
import it.bitprojects.store.entity.User;
import it.bitprojects.store.repository.UserRepository;

@Service
public class UserDetailsManagerImpl extends UserDetailsServiceImpl implements UserDetailsManager {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void createUser(UserDetails userDetails) {

		// user di dominio
		User user = new User();
		user.setActive(true);
		user.setUsername(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		user.setRoles(userDetails.getAuthorities().stream().map(
				
				auth -> {
			
			Role role = new Role();
			role.setRoleType(RoleType.valueOf(auth.getAuthority()));
			return role;
			
			}).collect(Collectors.toSet()) );
		
		userRepository.save(user);
		
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
