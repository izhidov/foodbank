package com.inzami.fp.rest.dto.update;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class ClientUpdateDTO {

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
    @Pattern(regexp = "\\d\\d/\\d\\d/\\d\\d\\d\\d", message = "Incorrect format. Use dd/mm/yyyy")
    private String birthDate;
    @NotBlank
    @Email
    private String email;
    private String phone;
    @Pattern(regexp = "\\d\\d\\d\\d", message = "Incorrect format. 4 digits.")
    private String ssn;
    private String spouseSsn;
}
