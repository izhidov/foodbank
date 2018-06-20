package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Organization;
import com.inzami.fp.domain.User;
import com.inzami.fp.repository.OrganizationRepository;
import com.inzami.fp.rest.dto.save.OrganizationSaveDTO;
import com.inzami.fp.rest.dto.save.UserSaveDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSaveDTOToDomainMapper extends CustomMapper<UserSaveDTO, User> {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void mapAtoB(UserSaveDTO userSaveDTO, User user, MappingContext context) {

        String encodedPassword = passwordEncoder.encode(userSaveDTO.getPassword());
        user.setPassword(encodedPassword);
        Organization organization = organizationRepository.findOne(userSaveDTO.getOrganizationId());
        user.setOrganization(organization);
        user.setEmail(user.getEmail().toLowerCase());
    }
}
