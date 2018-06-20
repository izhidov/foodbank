package com.inzami.fp.rest.dto.view;

import com.inzami.fp.enums.OrganizationType;
import com.inzami.fp.enums.Role;
import lombok.Data;

@Data
public class UserViewDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean active;
    private Long organizationId;
    private String organizationName;
    private OrganizationType organizationType;
    private Role role;
}
