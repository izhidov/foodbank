package com.inzami.fp.rest.dto.view;

import lombok.Data;

@Data
public class ClientLightViewDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
}
