package com.inzami.fp.inner.security;

import com.inzami.fp.exception.UserDeactivatedForActionException;
import com.inzami.fp.exception.WrongUserRoleForActionException;
import com.inzami.fp.enums.PermissionType;
import com.inzami.fp.util.AuthUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class AppSpringSecurityExpressionService {

    public boolean hasAnyAuthority(String... authorities) {
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Set<String> roleSet = AuthorityUtils.authorityListToSet(userAuthorities);

        org.springframework.security.core.userdetails.User authUser = AuthUtils.getAuthUser();
        if (!authUser.isEnabled()) {
            throw new UserDeactivatedForActionException("Please activate user before use this resource");
        }

        for (String role : authorities) {
            if (roleSet.contains(role)) {
                return true;
            }
        }

        String[] rolesArray = roleSet.toArray(new String[roleSet.size()]);
        throw new WrongUserRoleForActionException(rolesArray, authorities, authUser.getUsername());
    }

    public boolean hasIssueDocumentPermission() {
        return hasAnyAuthority(PermissionType.ISSUE_DOCUMENT.name());
    }

    public boolean hasValidateDocumentPermission() {
        return hasAnyAuthority(PermissionType.VALIDATE_DOCUMENT.name());
    }
}

