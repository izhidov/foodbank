package com.inzami.fp.rest.dto.view;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class ClientViewDTO {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address1;
    private String address2;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String zip;
//    @Pattern(regexp = "\\d\\d/\\d\\d/\\d\\d\\d\\d", message = "Incorrect format. Use MM/dd/yyyy")
    private String birthDate;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String ssn;
    @NotBlank
    private String spouseSsn;
}
