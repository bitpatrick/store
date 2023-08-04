package it.bitprojects.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_roles_generator")
	@SequenceGenerator(name = "auth_roles_generator", sequenceName = "auth_roles_seq", allocationSize = 1)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	public enum RoleType {
		ROLE_USER, ROLE_ADMIN;
	}

}
