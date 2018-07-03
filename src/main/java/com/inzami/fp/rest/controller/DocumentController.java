package com.inzami.fp.rest.controller;

import com.inzami.fp.config.ConfigurationProperties;
import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Document;
import com.inzami.fp.rest.dto.JsonResponse;
import com.inzami.fp.rest.dto.save.DocumentSaveDTO;
import com.inzami.fp.rest.dto.search.CheckDocumentReceivedDTO;
import com.inzami.fp.rest.dto.search.DocumentSearchForm;
import com.inzami.fp.rest.dto.update.MemberUpdateDTO;
import com.inzami.fp.rest.dto.view.DocumentLightViewDTO;
import com.inzami.fp.rest.dto.view.DocumentViewDTO;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import com.inzami.fp.service.ClientService;
import com.inzami.fp.service.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@Controller
@RequestMapping("/api/document")
@Slf4j
@Api(description = "Operations with documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping("/precreate/fromDocument/{documentId}")
    @ApiOperation(value = "Pre create new document from existed document")
    public String preCreateFromDocument(@PathVariable Long documentId, Model model) {
        DocumentSaveDTO documentSaveDTO = documentService.preCreateFromDocument(documentId);
        model.addAttribute("document", documentSaveDTO);
        return "document/create";
    }

    @GetMapping("/precreate/fromClient/{clientId}")
    @ApiOperation(value = "Pre create new document for client")
    public String preCreateFromClient(@PathVariable Long clientId, Model model) {
        DocumentSaveDTO documentSaveDTO = documentService.preCreateFromClient(clientId);
        model.addAttribute("document", documentSaveDTO);
        return "document/create";
    }

    @GetMapping(value = "/check/{clientId}")
    @ApiOperation(value = "Check if client already has received document")
    @ResponseBody
    public JsonResponse<CheckDocumentReceivedDTO> checkAlreadyReceived(@PathVariable Long clientId) throws Exception {
        Integer weeks = ConfigurationProperties.getIntegerProperty(ConfigurationProperties.VISIT_INTERVAL_LIMIT_WEEKS);
        Boolean hasAlreadyReceived = documentService.checkAlreadyReceived(clientId, weeks);
        CheckDocumentReceivedDTO checkDocumentReceivedDTO = new CheckDocumentReceivedDTO();
        checkDocumentReceivedDTO.setReceived(hasAlreadyReceived);
        checkDocumentReceivedDTO.setWeeks(weeks);
        return JsonResponse.success(checkDocumentReceivedDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(value = "Create new document")
    public String createDocument(@ModelAttribute("document") DocumentSaveDTO documentSaveDTO, Model model) {

        List<MemberUpdateDTO> members = documentSaveDTO.getMembers();
        if (CollectionUtils.isNotEmpty(members)) {
            List<MemberUpdateDTO> notEmptyMembers = members.stream().filter(member -> !Stream.of(member.getFirstName(), member.getLastName(),
                    member.getBirthDate()).allMatch(StringUtils::isBlank)).collect(Collectors.toList());
            boolean hasErrors = notEmptyMembers.stream().anyMatch(member -> Stream.of(member.getFirstName(), member.getLastName(),
                    member.getBirthDate()).anyMatch(StringUtils::isBlank));
            documentSaveDTO.setMembers(notEmptyMembers);

            if (hasErrors) {
                model.addAttribute("document", documentSaveDTO);
                return "document/create";
            }
        }

        Document document = documentService.create(documentSaveDTO);
        return "redirect:/api/document/" + document.getId();
    }

    @GetMapping("/list/{clientId}")
    @ApiOperation(value = "Search documents by client")
    @PreAuthorize("isAuthenticated()")
    public String findByClient(@PathVariable(value = "clientId") Long clientId, Model model) {
        Client client = clientService.findOne(clientId);
        model.addAttribute("clientName", client.getFirstName() + " " + client.getLastName());
        List<Document> documents = documentService.findByClient(client);
        List<DocumentLightViewDTO> documentLightViewDTOS = mapperFacade.mapAsList(documents, DocumentLightViewDTO.class);
        model.addAttribute("documentList", documentLightViewDTOS);
        return "document/list";
    }

    @PostMapping("/{documentId}/validate")
    @ApiOperation(value = "Validate document")
    @PreAuthorize("isAuthenticated()")
    public String validate(@PathVariable(value = "documentId") Long documentId) {
        documentService.validate(documentId);
        return "redirect:/api/document/" + documentId;
    }

    @GetMapping("/search")
    @ApiOperation(value = "Find document by number")
    @PreAuthorize("isAuthenticated()")
    public String findByDocumentNumber(@Valid DocumentSearchForm documentSearchForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("documentSearchForm", documentSearchForm);
            return "document";
        }
        Document document = documentService.findByDocumentNumber(documentSearchForm.getDocumentNumber());
        DocumentViewDTO documentViewDTO = mapperFacade.map(document, DocumentViewDTO.class);
        model.addAttribute("document", documentViewDTO);
        return "document";
    }

    @GetMapping("/{documentId}")
    @ApiOperation(value = "Find document by id")
    @PreAuthorize("isAuthenticated()")
    public String findByDocumentNumber(@PathVariable Long documentId, Model model) {
        Document document = documentService.findOne(documentId);
        DocumentSearchForm documentSearchForm = new DocumentSearchForm();
        documentSearchForm.setDocumentNumber(document.getNumber());
        model.addAttribute("documentSearchForm", documentSearchForm);
        DocumentViewDTO documentViewDTO = mapperFacade.map(document, DocumentViewDTO.class);
        model.addAttribute("document", documentViewDTO);
        return "document";
    }

    @GetMapping(value = "/pdf/preview/{documentId}", produces = APPLICATION_PDF_VALUE)
    @ApiOperation(value = "Preview pdf file")
    @ResponseBody
    public Resource previewPdf(@PathVariable Long documentId, HttpServletResponse response) throws Exception {
        File file = documentService.getPdf(documentId);
        response.setContentType(APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        return new FileSystemResource(file);
    }

    @GetMapping(value = "/pdf/download/{documentId}", produces = APPLICATION_PDF_VALUE)
    @ApiOperation(value = "Download pdf file")
    @ResponseBody
    public void downloadPdf(@PathVariable Long documentId, HttpServletResponse response) throws Exception {
        File file = documentService.getPdf(documentId);
        InputStream in = new FileInputStream(file);
        response.setContentType(APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }
}
