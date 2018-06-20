package com.inzami.fp.rest.dto.view;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MemberPdfViewDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Long age;
}
