package it.bitprojects.store.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JAVA BEAN
 */
@Setter
@Getter
public class LoginRequest {
	private String username;
	private String password;
}