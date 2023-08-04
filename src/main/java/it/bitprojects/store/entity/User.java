package it.bitprojects.store.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
	@SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
	private Integer id;
	@Column(unique = true, nullable = false)
	private String username;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private Boolean active;
	private String password;
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = 
	@JoinColumn(table = "users", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(table = "roles", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

}
