package com.inzami.fp.rest.dto.search;

import lombok.Data;

@Data
public class CheckDocumentReceivedDTO {

    private Boolean received;
    private Integer weeks;
}
