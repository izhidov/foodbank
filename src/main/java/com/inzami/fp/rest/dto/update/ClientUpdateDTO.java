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
    @Email
    private String email;
    private String phone;

    private String homeless;
    private String gender;
    private String race;
    private String otherRace;
    private String maritalStatus;
    private String militaryStatus;
    private String employment;
    private String governmentBenefits;
}
