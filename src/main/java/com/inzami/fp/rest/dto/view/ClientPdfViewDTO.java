package com.inzami.fp.rest.dto.view;

import lombok.Data;

@Data
public class ClientPdfViewDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String birthDate;
    private String email;
    private String phone;

    private Boolean homeless;
    private String gender;
    private String race;
    private String otherRace;
    private String maritalStatus;
    private String militaryStatus;
    private String employment;
    private String governmentBenefits;

}
