package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Client;
import com.inzami.fp.domain.Organization;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rest.dto.save.OrganizationSaveDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class OrganizationSaveDTOToDomainMapper extends CustomMapper<OrganizationSaveDTO, Organization> {

    @Override
    public void mapAtoB(OrganizationSaveDTO organizationSaveDTO, Organization organization, MappingContext context) {

    }
}
