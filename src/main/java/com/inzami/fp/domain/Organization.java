package com.inzami.fp.domain;

import com.inzami.fp.enums.OrganizationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "organization")
@EqualsAndHashCode(callSuper = false)
public class Organization extends AbstractAuditingEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;
    private Boolean active = true;
	@Enumerated(EnumType.STRING)
    private OrganizationType type;
}
