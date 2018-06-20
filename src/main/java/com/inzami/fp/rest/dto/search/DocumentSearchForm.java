package com.inzami.fp.rest.dto.search;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DocumentSearchForm {

    @NotBlank
    private String documentNumber;
}
