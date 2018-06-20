package com.inzami.fp.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OrganizationType {

    AGENT(PermissionType.ISSUE_DOCUMENT),
    DISPENSER(PermissionType.VALIDATE_DOCUMENT),
    BASE(PermissionType.ISSUE_DOCUMENT, PermissionType.VALIDATE_DOCUMENT);

    private List<PermissionType> permissions = new ArrayList<>();

    OrganizationType(PermissionType... permissions) {
        this.permissions = Arrays.asList(permissions);
    }

    public List<PermissionType> getPermissions() {
        return permissions;
    }
}
