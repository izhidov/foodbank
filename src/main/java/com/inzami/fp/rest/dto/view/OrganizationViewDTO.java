package com.inzami.fp.rest.dto.view;

import com.inzami.fp.domain.AbstractAuditingEntity;
import com.inzami.fp.enums.OrganizationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
public class OrganizationViewDTO{

    private Long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;
    private Boolean active;
    private OrganizationType type;
}
