package com.inzami.fp.service;

import com.inzami.fp.config.ConfigurationProperties;
import com.inzami.fp.domain.*;
import com.inzami.fp.enums.DocumentType;
import com.inzami.fp.exception.BadRequestException;
import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.repository.DocumentMemberRepository;
import com.inzami.fp.repository.DocumentRepository;
import com.inzami.fp.repository.MemberRepository;
import com.inzami.fp.repository.OrganizationRepository;
import com.inzami.fp.rest.dto.save.DocumentSaveDTO;
import com.inzami.fp.rest.dto.save.OrganizationSaveDTO;
import com.inzami.fp.rest.dto.search.ClientSearchForm;
import com.inzami.fp.rest.dto.search.OrganizationSearchForm;
import com.inzami.fp.rest.dto.update.OrganizationUpdateDTO;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import com.inzami.fp.rest.dto.view.DocumentPdfViewDTO;
import com.inzami.fp.rest.dto.view.MemberViewDTO;
import com.inzami.fp.rest.dto.view.OrganizationSelectViewDTO;
import com.inzami.fp.util.DocumentUtils;
import com.inzami.fp.util.PdfGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private MapperFacade mapperFacade;

    public Organization findOne(Long id) throws EntityNotFoundInServiceException {
        Organization result = organizationRepository.findOne(id);
        if (result == null) {
            throw new EntityNotFoundInServiceException(Organization.class, id);
        }
        return result;
    }

    @Transactional
    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Transactional
    public Organization saveAndFlush(Organization organization) {
        return organizationRepository.saveAndFlush(organization);
    }

    @Transactional
    public List<OrganizationSelectViewDTO> getOrganizationForSelect(){
        List<Organization> organizations = organizationRepository.findAll();
        List<OrganizationSelectViewDTO> viewDTOList = organizations.stream().map(organization -> {
            OrganizationSelectViewDTO selectViewDTO = new OrganizationSelectViewDTO();
            selectViewDTO.setId(organization.getId());
            selectViewDTO.setDescription(organization.getName() + " (" + organization.getType() + ")");
            return selectViewDTO;
        }).collect(Collectors.toList());
        return viewDTOList;
    }

    public List<Organization> searchOrganizations(OrganizationSearchForm organizationSearchForm) {
        Pageable pageable = new PageRequest(organizationSearchForm.getPage(), organizationSearchForm.getLimit());
        List<Organization> organizations = organizationRepository.findByNameLike(organizationSearchForm.getOrganizationName(), pageable);
        return organizations;
    }

    public Organization update(Long organizationId, OrganizationUpdateDTO organizationUpdateDTO) {
        Organization organization = findOne(organizationId);
        if (StringUtils.isNotBlank(organizationUpdateDTO.getName())) {
            organization.setName(organizationUpdateDTO.getName());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getAddress1())) {
            organization.setAddress1(organizationUpdateDTO.getAddress1());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getAddress2())) {
            organization.setAddress2(organizationUpdateDTO.getAddress2());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getCity())) {
            organization.setCity(organizationUpdateDTO.getCity());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getState())) {
            organization.setState(organizationUpdateDTO.getState());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getZip())) {
            organization.setZip(organizationUpdateDTO.getZip());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getEmail())) {
            organization.setEmail(organizationUpdateDTO.getEmail());
        }
        if (StringUtils.isNotBlank(organizationUpdateDTO.getPhone())) {
            organization.setPhone(organizationUpdateDTO.getPhone());
        }
        if (organizationUpdateDTO.getActive() != null) {
            organization.setActive(organizationUpdateDTO.getActive());
        }
        if (organizationUpdateDTO.getType() != null) {
            organization.setType(organizationUpdateDTO.getType());
        }

        organization = organizationRepository.save(organization);
        return organization;
    }

    public Organization create(OrganizationSaveDTO organizationSaveDTO) {
        Organization organization = mapperFacade.map(organizationSaveDTO, Organization.class);
        save(organization);
        return organization;
    }
}
