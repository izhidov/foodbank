package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Document;
import com.inzami.fp.rest.dto.view.DocumentViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DocumentToViewDTOMapper extends CustomMapper<Document, DocumentViewDTO> {

    @Override
    public void mapAtoB(Document document, DocumentViewDTO documentViewDTO, MappingContext context) {
        documentViewDTO.setIssuedUserId(document.getIssuedUser().getId());
        documentViewDTO.setIssuedUserEmail(document.getIssuedUser().getEmail());
        documentViewDTO.setIssuedOrganizationId(document.getIssuedOrganization().getId());
        documentViewDTO.setIssuedOrganizationName(document.getIssuedOrganization().getName());
        if(document.getPostedUser() != null){
            documentViewDTO.setPostedUserId(document.getPostedUser().getId());
            documentViewDTO.setPostedUserEmail(document.getPostedUser().getEmail());
        }
        if(document.getPostedOrganization() != null){
            documentViewDTO.setPostedOrganizationId(document.getPostedOrganization().getId());
            documentViewDTO.setPostedOrganizationName(document.getPostedOrganization().getName());
        }
        if(document.getPostedAt() != null) {
            String postedAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getPostedAt());
            documentViewDTO.setPostedAt(postedAt);
        }
        String expiredAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getExpiredAt());
        documentViewDTO.setExpiredAt(expiredAt);
        String createdAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getCreatedAt());
        documentViewDTO.setCreatedAt(createdAt);


    }
}
