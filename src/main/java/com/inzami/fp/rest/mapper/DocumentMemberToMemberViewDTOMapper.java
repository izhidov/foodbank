package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.DocumentMember;
import com.inzami.fp.domain.Member;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class DocumentMemberToMemberViewDTOMapper extends CustomMapper<DocumentMember, MemberViewDTO> {

    @Override
    public void mapAtoB(DocumentMember documentMember, MemberViewDTO memberViewDTO, MappingContext context) {
        Member member = documentMember.getMember();
        memberViewDTO.setId(member.getId());
        memberViewDTO.setFirstName(member.getFirstName());
        memberViewDTO.setLastName(member.getLastName());
        memberViewDTO.setBirthDate(member.getBirthDate());
    }
}
