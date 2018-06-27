package com.inzami.fp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "client")
@EqualsAndHashCode(callSuper = false)
public class Client extends AbstractAuditingEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    @Pattern(regexp = "\\d\\d/\\d\\d/\\d\\d\\d\\d", message = "Format MM/dd/yyyy")
    private String birthDate;
	private String email;
    private String phone;
}
