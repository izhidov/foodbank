package com.inzami.fp.repository;

import com.inzami.fp.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u where u.email=lower(:email)")
	Optional<User> findByEmail(String email);

	@Query("SELECT COUNT (u) FROM User u WHERE u.email = lower(:email) ")
	long countByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email like CONCAT(lower(:email), '%')")
	List<User> findByEmailLike(String email, Pageable pageable);
}