package com.inzami.fp.repository;

import com.inzami.fp.domain.Client;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	@Query(value = "SELECT DISTINCT c.firstName FROM Client c WHERE c.firstName like CONCAT(:firstName, '%') ORDER BY c.firstName")
	List<String> autocompleteByFirstName(String firstName, Pageable pageable);

	@Query(value = "SELECT DISTINCT c.lastName FROM Client c WHERE c.lastName like CONCAT(:lastName, '%') ORDER BY c.lastName")
	List<String> autocompleteByLastName(String lastName, Pageable pageable);

	List<Client> findBySsnStartingWith(String ssn, Pageable pageable);

	@Query(value = "SELECT c FROM Client c WHERE c.firstName like CONCAT(:firstName, '%') AND c.lastName like CONCAT(:lastName, '%') AND c.ssn like CONCAT(:ssn, '%') AND c.birthDate like CONCAT(:birthDate, '%') ORDER BY c.firstName")
	List<Client> findByFirstNameLikeAndLastNameLikeAndSnnLikeAndBirthDateLike(String firstName, String lastName, String ssn, String birthDate, Pageable pageable);

}