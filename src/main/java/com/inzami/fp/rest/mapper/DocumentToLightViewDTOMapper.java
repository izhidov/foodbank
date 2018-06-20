package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Document;
import com.inzami.fp.domain.DocumentMember;
import com.inzami.fp.rest.dto.view.DocumentLightViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DocumentToLightViewDTOMapper extends CustomMapper<Document, DocumentLightViewDTO> {

    @Override
    public void mapAtoB(Document document, DocumentLightViewDTO documentLightViewDTO, MappingContext context) {
        documentLightViewDTO.setId(document.getId());
        documentLightViewDTO.setType(document.getType());
        documentLightViewDTO.setNumber(document.getNumber());
        documentLightViewDTO.setIssuedOrganizationName(document.getIssuedOrganization().getName());
        documentLightViewDTO.setIssuedUserEmail(document.getIssuedUser().getEmail());
        if(document.getPostedOrganization() != null) {
            documentLightViewDTO.setPostedOrganizationName(document.getPostedOrganization().getEmail());
        }
        if(document.getPostedUser() != null) {
            documentLightViewDTO.setPostedUserEmail(document.getPostedUser().getEmail());
        }
        if(document.getPostedAt() != null) {
            String postedAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getPostedAt());
            documentLightViewDTO.setPostedAt(postedAt);
        }
        String expiredAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getExpiredAt());
        documentLightViewDTO.setExpiredAt(expiredAt);
        String createdAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getCreatedAt());
        documentLightViewDTO.setCreatedAt(createdAt);
    }
}
