package com.inzami.fp.repository;

import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByClientAndActiveTrue(Client client);
    List<Member> findByClientId(Long clientId);
    List<Member> findByClientIdAndActiveTrue(Long clientId);
}