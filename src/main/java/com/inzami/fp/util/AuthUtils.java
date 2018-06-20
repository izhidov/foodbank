package com.inzami.fp.util;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public final class AuthUtils {

    public static void logOutCurrentUser() {
        SecurityContextHolder.clearContext();
    }

    public static void relogin(UserDetails user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static User getAuthUser() {
        User authUser = null;
        if (!Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            Object authUserObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (authUserObject instanceof User) {
                authUser = (User) authUserObject;
            }

        }
        return authUser;
    }

}
