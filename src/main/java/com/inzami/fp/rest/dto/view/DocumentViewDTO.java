package com.inzami.fp.rest.dto.view;

import com.inzami.fp.enums.DocumentType;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class DocumentViewDTO {

    private Long id;
    private DocumentType type;
    private String number;
    private Long issuedUserId; // user who issued the doc
    private String issuedUserEmail; // user who issued the doc
    private Long issuedOrganizationId; // user org
    private String issuedOrganizationName; // user org
    private Long postedUserId; // user who validated the doc
    private String postedUserEmail; // user who validated the doc
    private Long postedOrganizationId; // dispenser org
    private String postedOrganizationName; // dispenser org
    private ClientViewDTO client;
    private String postedAt; // when doc was validated
    private String expiredAt;
    private String createdAt;
    private List<MemberViewDTO> members = new ArrayList<>();
}
