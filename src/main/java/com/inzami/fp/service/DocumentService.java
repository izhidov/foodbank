package com.inzami.fp.service;

import com.inzami.fp.config.ConfigurationProperties;
import com.inzami.fp.domain.*;
import com.inzami.fp.enums.DocumentType;
import com.inzami.fp.exception.BadRequestException;
import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.repository.DocumentMemberRepository;
import com.inzami.fp.repository.DocumentRepository;
import com.inzami.fp.repository.MemberRepository;
import com.inzami.fp.rest.dto.save.DocumentSaveDTO;
import com.inzami.fp.rest.dto.update.MemberUpdateDTO;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import com.inzami.fp.rest.dto.view.DocumentPdfViewDTO;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import com.inzami.fp.util.DocumentUtils;
import com.inzami.fp.util.PdfGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentMemberRepository documentMemberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private UserService userService;
    @Autowired
    private PdfGeneratorUtil pdfGeneratorUtil;
    @Autowired
    private MemberRepository memberRepository;

    public Document findOne(Long id) throws EntityNotFoundInServiceException {
        Document result = documentRepository.findOne(id);
        if (result == null) {
            throw new EntityNotFoundInServiceException(Document.class, id);
        }
        return result;
    }

    @Transactional
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Transactional
    public Document saveAndFlush(Document document) {
        return documentRepository.saveAndFlush(document);
    }

    @PreAuthorize("@appSpringSecurityExpressionService.hasIssueDocumentPermission()")
    @Transactional
    public DocumentSaveDTO preCreateFromClient(Long clientId) {
        Client client = clientService.findOne(clientId);
        return getDocumentSaveDTO(client);
    }

    private DocumentSaveDTO getDocumentSaveDTO(Client client) {
        String number = DocumentUtils.generateDocumentNumber(client, DocumentType.VOUCHER);
        ClientViewDTO clientViewDTO = mapperFacade.map(client, ClientViewDTO.class);
        DocumentSaveDTO documentSaveDTO = new DocumentSaveDTO();
        String createdAt = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(ZonedDateTime.now());
        documentSaveDTO.setCreatedAt(createdAt);
        documentSaveDTO.setNumber(number);
        documentSaveDTO.setClient(clientViewDTO);
        List<Member> members = memberRepository.findByClientAndActiveTrue(client);
        List<MemberUpdateDTO> memberUpdateDTOS = mapperFacade.mapAsList(members, MemberUpdateDTO.class);
        documentSaveDTO.setMembers(memberUpdateDTOS);
        return documentSaveDTO;
    }

    @PreAuthorize("@appSpringSecurityExpressionService.hasIssueDocumentPermission()")
    @Transactional
    public DocumentSaveDTO preCreateFromDocument(Long documentId) {
        Document document = findOne(documentId);
        Client client = document.getClient();
        return getDocumentSaveDTO(client);
    }

    @PreAuthorize("@appSpringSecurityExpressionService.hasIssueDocumentPermission()")
    @Transactional
    public Document create(DocumentSaveDTO documentSaveDTO) {
        Client client = clientService.findOne(documentSaveDTO.getClient().getId());

        User authUser = userService.getCurrentUser();
        Document document = new Document();
        document.setNumber(DocumentUtils.generateDocumentNumber(client, documentSaveDTO.getType()));
        document.setClient(client);
        document.setIssuedUser(authUser);
        document.setIssuedOrganization(authUser.getOrganization());
        ZonedDateTime expiredAt = ZonedDateTime.now()
                .plusDays(ConfigurationProperties.getIntegerProperty(ConfigurationProperties.VOUCHER_VALID_FOR_DAYS));
        document.setExpiredAt(expiredAt);
        if (StringUtils.isNotBlank(documentSaveDTO.getCreatedAt())) {
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate createdAtDate = LocalDate.parse(documentSaveDTO.getCreatedAt(), parser);
            ZonedDateTime createdAt = createdAtDate.atStartOfDay(ZoneId.systemDefault());
            document.setCreatedAt(createdAt);
        }
        save(document);

        if (CollectionUtils.isNotEmpty(documentSaveDTO.getMembers())) {
            List<Member> members = memberService.saveMembers(client.getId(), documentSaveDTO.getMembers());
            members.forEach(member -> {
                        DocumentMember documentMember = new DocumentMember();
                        documentMember.setClient(client);
                        documentMember.setDocument(document);
                        documentMember.setMember(member);
                        documentMemberRepository.save(documentMember);
                    });
        }

        return document;
    }

    public List<Document> findByClient(Client client) {
        List<Document> documents = documentRepository.findByClient(client);
        return documents;
    }

    public Document findByDocumentNumber(String documentNumber) {
        List<Document> documents = documentRepository.findFirstByNumberLike(documentNumber, new PageRequest(0, 1));
        return documents.iterator().hasNext() ? documents.iterator().next() : null;
    }

    @PreAuthorize("@appSpringSecurityExpressionService.hasValidateDocumentPermission()")
    @Transactional
    public void validate(Long documentId) {
        Document document = documentRepository.findOne(documentId);
        if (document.getPostedAt() != null) {
            throw new BadRequestException("Document has been already validated");
        }
        User persistentUser = userService.getCurrentUser();
        document.setPostedUser(persistentUser);
        document.setPostedOrganization(persistentUser.getOrganization());
        document.setPostedAt(ZonedDateTime.now());
        documentRepository.save(document);
    }

    public File getPdf(Long documentId) throws Exception {
        Document document = findOne(documentId);
        DocumentPdfViewDTO documentPdfViewDTO = mapperFacade.map(document, DocumentPdfViewDTO.class);
        // TODO: 29.05.2018 check template according to document type or user organisation
        File file = pdfGeneratorUtil.createPdf("voucherTemplate", documentPdfViewDTO);
        return file;
    }

    public Boolean checkAlreadyReceived(Long clientId, Integer weeks) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now().minusWeeks(weeks);
        Long count = documentRepository.countByClientIdAndCreatedAtAfter(clientId, zonedDateTime);
        return count > 0;
    }
}
