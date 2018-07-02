package com.inzami.fp.rest.dto.update;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberUpdateDTO {

    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String birthDate;
    private Boolean active;
}
