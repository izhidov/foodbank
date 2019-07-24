package com.inzami.fp.rest.dto.view;

import com.inzami.fp.enums.DocumentType;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentPdfViewDTO {

    private DocumentType type;
    private String organizationName;
    private String number;
    private ClientPdfViewDTO client;
    private String expiredAt;
    private String createdAt;
    private String eligibilityDate;
    private List<MemberPdfViewDTO> members = new ArrayList<>();
}
