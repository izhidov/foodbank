package com.inzami.fp.inner.security;

import com.inzami.fp.domain.Organization;
import com.inzami.fp.domain.User;
import com.inzami.fp.enums.PermissionType;
import com.inzami.fp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);
        User user = userService.findByEmail(email);
        Organization organization = user.getOrganization();
        List<PermissionType> permissions = organization.getType().getPermissions();
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(user.getRole().name());
        permissions.forEach( permission ->
                authorityList.add(new SimpleGrantedAuthority(permission.name()))
        );
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getActive(), true, true, true,
                authorityList);
    }
}

