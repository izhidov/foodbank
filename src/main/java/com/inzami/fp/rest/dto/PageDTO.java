package com.inzami.fp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class PageDTO {

    private Integer page = 0;
    private Integer size = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortProperty;

    public PageRequest toPageRequest() {
        Sort sort = new Sort(sortDirection, sortProperty);
        return new PageRequest(page, size, sort);
    }
}
