package it.bitprojects.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.bitprojects.store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//List<Person> findByAddressZipCode(ZipCode zipCode);

}
