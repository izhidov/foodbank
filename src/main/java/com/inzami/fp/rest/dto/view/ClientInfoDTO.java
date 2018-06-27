package com.inzami.fp.rest.dto.view;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ClientInfoDTO {

    private Long id;
    private String info;
}
