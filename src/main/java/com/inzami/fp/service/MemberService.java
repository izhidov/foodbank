package com.inzami.fp.service;

import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Member;
import com.inzami.fp.domain.User;
import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.repository.ClientRepository;
import com.inzami.fp.repository.DocumentRepository;
import com.inzami.fp.repository.MemberRepository;
import com.inzami.fp.repository.UserRepository;
import com.inzami.fp.rest.dto.update.MemberUpdateDTO;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import com.inzami.fp.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Member saveOrUpdate(MemberViewDTO memberViewDTO, Client client) {
        Member member = null;
        if(memberViewDTO.getId() != null){
            member = memberRepository.findOne(memberViewDTO.getId());
        }
        if(member == null){
            member = new Member();
            member.setClient(client);
        }
        member.setFirstName(memberViewDTO.getFirstName());
        member.setLastName(memberViewDTO.getLastName());
        member.setBirthDate(memberViewDTO.getBirthDate());
        Member save = memberRepository.save(member);
        return save;
    }

    public void saveMembers(Long clientId, List<MemberUpdateDTO> membersUpdateDTOS){
        Client client = clientRepository.findOne(clientId);
        List<Member> memberList = memberRepository.findByClientId(clientId);
        memberList.forEach(member -> {
            member.setActive(false);
            memberRepository.save(member);
        });
        if(CollectionUtils.isNotEmpty(membersUpdateDTOS)) {
            membersUpdateDTOS.forEach(memberUpdateDTO -> {
                Member member;
                if(memberUpdateDTO.getId() != null) {
                    member = memberRepository.findOne(memberUpdateDTO.getId());
                } else {
                    member = new Member();
                    member.setClient(client);
                }
                member.setFirstName(memberUpdateDTO.getFirstName());
                member.setLastName(memberUpdateDTO.getLastName());
                member.setBirthDate(memberUpdateDTO.getBirthDate());
                member.setActive(memberUpdateDTO.getActive());
                memberRepository.save(member);
            });
        }
    }
}
