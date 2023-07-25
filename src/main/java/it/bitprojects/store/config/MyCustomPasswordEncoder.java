package it.bitprojects.store.config;

import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyCustomPasswordEncoder extends DelegatingPasswordEncoder {

	public MyCustomPasswordEncoder(String idForEncode, Map<String, PasswordEncoder> idToPasswordEncoder) {
		super(idForEncode, idToPasswordEncoder);
		// TODO Auto-generated constructor stub
	}
	
	

	public MyCustomPasswordEncoder(String idForEncode, Map<String, PasswordEncoder> idToPasswordEncoder,
			String idPrefix, String idSuffix) {
		super(idForEncode, idToPasswordEncoder, idPrefix, idSuffix);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean matches(CharSequence rawPassword, String prefixEncodedPassword) {

		if ( rawPassword == null || rawPassword.isEmpty() ) {
			
			throw new BadCredentialsException("hai inserito un password null o empty");
		}
		
		return super.matches(rawPassword, prefixEncodedPassword);
	}

}
