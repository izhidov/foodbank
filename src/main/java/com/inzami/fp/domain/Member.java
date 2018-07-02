package com.inzami.fp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "member")
@EqualsAndHashCode(callSuper = false)
public class Member extends AbstractAuditingEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "\\d\\d/\\d\\d/\\d\\d\\d\\d", message = "Format MM/dd/yyyy")
    private String birthDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    private Boolean active;
}
