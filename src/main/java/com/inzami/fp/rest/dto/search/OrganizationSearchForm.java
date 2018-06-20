package com.inzami.fp.rest.dto.search;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class OrganizationSearchForm {

    private String organizationName = "";
    private Integer page = 0;
    private Integer limit = 20;
}
