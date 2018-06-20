package com.inzami.fp.rest.mapper;

import com.inzami.fp.config.ConfigurationProperties;
import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Document;
import com.inzami.fp.domain.DocumentMember;
import com.inzami.fp.domain.Member;
import com.inzami.fp.rest.dto.view.DocumentPdfViewDTO;
import com.inzami.fp.rest.dto.view.MemberPdfViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.inzami.fp.config.ConfigurationProperties.VISIT_INTERVAL_LIMIT_WEEKS;

@Component
public class DocumentToPdfViewDTOMapper extends CustomMapper<Document, DocumentPdfViewDTO> {

    @Override
    public void mapAtoB(Document document, DocumentPdfViewDTO documentPdfViewDTO, MappingContext context) {
        List<DocumentMember> documentMembers = document.getMembers();
        List<Member> members = documentMembers.stream().map(DocumentMember::getMember).collect(Collectors.toList());
        //add member for client
        Client client = document.getClient();
        Member clientMember = new Member();
        clientMember.setFirstName(client.getFirstName());
        clientMember.setLastName(client.getLastName());
        clientMember.setBirthDate(client.getBirthDate());

        List<Member> memberList = new ArrayList<>();
        memberList.add(clientMember);
        memberList.addAll(members);

        documentPdfViewDTO.setNumber(document.getNumber());
        documentPdfViewDTO.setType(document.getType());
        documentPdfViewDTO.setOrganizationName(document.getIssuedOrganization().getName());
        String expiredAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getExpiredAt());
        documentPdfViewDTO.setExpiredAt(expiredAt);
        String createdAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(document.getCreatedAt());
        documentPdfViewDTO.setCreatedAt(createdAt);
        Integer limitWeeks = ConfigurationProperties.getIntegerProperty(VISIT_INTERVAL_LIMIT_WEEKS);
        ZonedDateTime eligibilityDate = document.getCreatedAt().plusWeeks(limitWeeks);
        String eligibilityDateStr = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(eligibilityDate);
        documentPdfViewDTO.setEligibilityDate(eligibilityDateStr);

        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter parser2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<MemberPdfViewDTO> pdfMembers = memberList.stream().map(member -> {
            MemberPdfViewDTO memberPdfViewDTO = new MemberPdfViewDTO();
            memberPdfViewDTO.setFirstName(member.getFirstName());
            memberPdfViewDTO.setLastName(member.getLastName());
            memberPdfViewDTO.setBirthDate(member.getBirthDate());
            LocalDate birthDateTime;
            if(member.getBirthDate().contains("/")) {
                birthDateTime = LocalDate.parse(member.getBirthDate(), parser);
            } else {
                birthDateTime = LocalDate.parse(member.getBirthDate(), parser2);
            }
            long age = ChronoUnit.YEARS.between(birthDateTime, LocalDateTime.now());
            memberPdfViewDTO.setAge(age);
            return memberPdfViewDTO;
        }).collect(Collectors.toList());
        documentPdfViewDTO.setMembers(pdfMembers);
    }
}
