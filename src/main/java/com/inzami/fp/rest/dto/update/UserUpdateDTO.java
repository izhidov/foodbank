package com.inzami.fp.rest.dto.update;

import com.inzami.fp.enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private String phone;
    private Boolean active;
    @NotNull
    private Long organizationId;
    @NotNull
    private Role role;
}
