package com.inzami.fp.domain;

import com.inzami.fp.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
@EqualsAndHashCode(exclude = {"organization"}, callSuper = false)
public class User extends AbstractAuditingEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 5)
	@NotBlank
    private String password;
	@NotBlank
    private String firstName;
	@NotBlank
    private String lastName;
    @Email
	@NotBlank
    private String email;
    private String phone;
    private Boolean active = true;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;
	@Enumerated(EnumType.STRING)
	private Role role;
}
