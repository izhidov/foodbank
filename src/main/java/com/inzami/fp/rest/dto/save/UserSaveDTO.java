package com.inzami.fp.rest.dto.save;

import com.inzami.fp.enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class UserSaveDTO {
    @NotBlank
    @Length(min = 5)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;
    private String phone;
    private Boolean active = true;
    @NotNull
    private Long organizationId;
    @NotNull
    private Role role;
}
