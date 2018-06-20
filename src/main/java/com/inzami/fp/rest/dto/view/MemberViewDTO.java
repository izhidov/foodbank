package com.inzami.fp.rest.dto.view;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MemberViewDTO {

    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
//    @Pattern(regexp = "\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d", message = "Format MM/dd/yyyy")
    private String birthDate;
}
