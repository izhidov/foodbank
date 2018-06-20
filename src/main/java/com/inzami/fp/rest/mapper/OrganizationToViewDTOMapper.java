package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Organization;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import com.inzami.fp.rest.dto.view.OrganizationViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class OrganizationToViewDTOMapper extends CustomMapper<Organization, OrganizationViewDTO> {

    @Override
    public void mapAtoB(Organization organization, OrganizationViewDTO organizationViewDTO, MappingContext context) {
    }
}
