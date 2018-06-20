package com.inzami.fp.rest.dto.view;

import com.inzami.fp.enums.DocumentType;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class DocumentLightViewDTO {

    private Long id;
    private DocumentType type;
    private String number;
    private String issuedUserEmail;
    private String issuedOrganizationName;
    private String postedUserEmail;
    private String postedOrganizationName;
    private String postedAt;
    private String expiredAt;
    private String createdAt;
}
