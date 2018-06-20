package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Member;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class MemberToViewDTOMapper extends CustomMapper<Member, MemberViewDTO> {

    @Override
    public void mapAtoB(Member member, MemberViewDTO memberViewDTO, MappingContext context) {
    }
}
