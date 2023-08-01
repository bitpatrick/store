package it.bitprojects.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {
	@Column
	@Id
	private String address;

}
