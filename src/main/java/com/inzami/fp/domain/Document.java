package com.inzami.fp.domain;

import com.inzami.fp.enums.DocumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "document")
@EqualsAndHashCode(of = {"id", "number"}, callSuper = false)
@ToString(of = {"id", "number"})
public class Document extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Enumerated(EnumType.STRING)
	private DocumentType type = DocumentType.VOUCHER;
	private String number;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issued_user_id", referencedColumnName = "id")
    private User issuedUser; // user who issued the doc
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issued_organization_id", referencedColumnName = "id")
    private Organization issuedOrganization; // user org
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posted_user_id", referencedColumnName = "id")
    private User postedUser; // user who validated the doc
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posted_organization_id", referencedColumnName = "id")
    private Organization postedOrganization; // dispenser org
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    private ZonedDateTime postedAt; // when doc was validated
    private ZonedDateTime expiredAt;
	@OneToMany(cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY,
			mappedBy = "document")
	private List<DocumentMember> members = new ArrayList<>();

}
