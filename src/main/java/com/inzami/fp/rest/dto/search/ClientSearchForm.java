package com.inzami.fp.rest.dto.search;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ClientSearchForm {

    private String ssn;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Integer page = 0;
    private Integer limit = 20;
}
