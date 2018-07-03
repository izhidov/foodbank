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

	@Query(value = "SELECT c FROM Client c WHERE c.firstName like CONCAT(:firstName, '%') AND c.lastName like CONCAT(:lastName, '%') AND c.birthDate like CONCAT(:birthDate, '%') ORDER BY c.firstName")
	List<Client> findByFirstNameLikeAndLastNameLikeAndBirthDateLike(String firstName, String lastName, String birthDate, Pageable pageable);

	List<Client> findByBirthDate(String birthDate, Pageable pageable);
}