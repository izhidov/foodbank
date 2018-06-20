package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Organization;
import com.inzami.fp.domain.User;
import com.inzami.fp.rest.dto.view.OrganizationViewDTO;
import com.inzami.fp.rest.dto.view.UserViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class UserToViewDTOMapper extends CustomMapper<User, UserViewDTO> {

    @Override
    public void mapAtoB(User user, UserViewDTO userViewDTO, MappingContext context) {
        Organization organization = user.getOrganization();
        userViewDTO.setOrganizationId(organization.getId());
        userViewDTO.setOrganizationName(organization.getName());
        userViewDTO.setOrganizationType(organization.getType());
    }
}
