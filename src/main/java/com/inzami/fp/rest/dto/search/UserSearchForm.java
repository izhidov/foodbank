package com.inzami.fp.rest.dto.search;

import lombok.Data;

@Data
public class UserSearchForm {

    private String userEmail = "";
    private Integer page = 0;
    private Integer limit = 20;
}
