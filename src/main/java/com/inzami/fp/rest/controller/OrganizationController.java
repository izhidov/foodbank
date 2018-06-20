package com.inzami.fp.rest.controller;

import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Document;
import com.inzami.fp.domain.Organization;
import com.inzami.fp.repository.OrganizationRepository;
import com.inzami.fp.rest.dto.JsonResponse;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rest.dto.save.DocumentSaveDTO;
import com.inzami.fp.rest.dto.save.OrganizationSaveDTO;
import com.inzami.fp.rest.dto.search.DocumentSearchForm;
import com.inzami.fp.rest.dto.search.OrganizationSearchForm;
import com.inzami.fp.rest.dto.update.ClientUpdateDTO;
import com.inzami.fp.rest.dto.update.OrganizationUpdateDTO;
import com.inzami.fp.rest.dto.view.*;
import com.inzami.fp.service.ClientService;
import com.inzami.fp.service.DocumentService;
import com.inzami.fp.service.OrganizationService;
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
@RequestMapping("/api/organization")
@Slf4j
@Api(description = "Operations with organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping("/search")
    @ApiOperation(value = "Find organization by name")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findByDocumentNumber(@Valid OrganizationSearchForm organizationSearchForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("organizationSearchForm", organizationSearchForm);
            return "organization";
        }
        List<Organization> organizations = organizationService.searchOrganizations(organizationSearchForm);
        List<OrganizationViewDTO> organizationViewDTOS = mapperFacade.mapAsList(organizations, OrganizationViewDTO.class);
        model.addAttribute("organizationList", organizationViewDTOS);
        return "organization";
    }

    // get by id
    @GetMapping("/{organizationId}")
    @ApiOperation(value = "Get by id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public JsonResponse<OrganizationViewDTO> getById(@PathVariable("organizationId") Long organizationId) {
        Organization organization = organizationRepository.findOne(organizationId);
        OrganizationViewDTO organizationViewDTO = mapperFacade.map(organization, OrganizationViewDTO.class);
        return JsonResponse.success(organizationViewDTO);
    }

    // update
    @PostMapping("/{organizationId}")
    @ApiOperation(value = "Update organization")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public JsonResponse update(@PathVariable("organizationId") Long organizationId,
                               @RequestBody @Valid OrganizationUpdateDTO organizationUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JsonResponse.formError(bindingResult.getAllErrors());
        }
        Organization organization = organizationService.update(organizationId, organizationUpdateDTO);
        return JsonResponse.success(organization.getId());
    }

    // create
    @PostMapping
    @ApiOperation(value = "Create organization")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public JsonResponse create(@RequestBody @Valid OrganizationSaveDTO organizationSaveDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JsonResponse.formError(bindingResult.getAllErrors());
        }
        Organization organization = organizationService.create(organizationSaveDTO);
        return JsonResponse.success(organization.getId());
    }
}
