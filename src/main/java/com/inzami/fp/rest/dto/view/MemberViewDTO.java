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
    private String birthDate;
    private Boolean active;
}
