package com.inzami.fp.repository;

import com.inzami.fp.domain.Document;
import com.inzami.fp.domain.Organization;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "SELECT o FROM Organization o WHERE o.name like CONCAT(:name, '%')")
    List<Organization> findByNameLike(String name, Pageable pageable);

}