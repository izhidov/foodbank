package com.inzami.fp.repository;

import com.inzami.fp.domain.DocumentMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMemberRepository extends JpaRepository<DocumentMember, Long> {

    List<DocumentMember> findByClientId(Long clientId);
}