package com.inzami.fp.rest.dto.update;

import com.inzami.fp.enums.OrganizationType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class OrganizationUpdateDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address1;
    private String address2;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String zip;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    private Boolean active;
    @NotNull
    private OrganizationType type;
}
