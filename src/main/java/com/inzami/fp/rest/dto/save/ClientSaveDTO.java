package com.inzami.fp.rest.dto.save;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class ClientSaveDTO {

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
    @Pattern(regexp = "\\d\\d/\\d\\d/\\d\\d\\d\\d", message = "Format MM/dd/yyyy")
    private String birthDate;
    @Email
    private String email;
    private String phone;
    private String ssn;
    private String spouseSsn;
}
