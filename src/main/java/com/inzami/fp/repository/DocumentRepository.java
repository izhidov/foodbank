package com.inzami.fp.repository;

import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Document;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByNumber(String number);

    @Query(value = "SELECT d FROM Document d WHERE d.number like CONCAT(:number, '%')")
    List<Document> findFirstByNumberLike(String number, Pageable pageable);

    List<Document> findByClient(Client client);
    Long countByClientIdAndCreatedAtAfter(Long clientId, ZonedDateTime zonedDateTime);

    Document findFirstByClientOrderByCreatedAtDesc(Client client);
}