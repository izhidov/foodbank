package com.inzami.fp.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.inzami.fp.FpApplication;
import com.inzami.fp.domain.*;
import com.inzami.fp.enums.DocumentType;
import com.inzami.fp.repository.ClientRepository;
import com.inzami.fp.repository.DocumentMemberRepository;
import com.inzami.fp.repository.DocumentRepository;
import com.inzami.fp.repository.MemberRepository;
import com.inzami.fp.rest.dto.save.DocumentSaveDTO;
import com.inzami.fp.rules.ResourceRule;
import com.inzami.fp.service.DocumentService;
import com.inzami.fp.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@Transactional
public class DocumentControllerTest {

    @Rule
    public ResourceRule resources = new ResourceRule();
    @Resource
    private WebApplicationContext webApplicationContext;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentMemberRepository documentMemberRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DocumentService documentService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @DatabaseSetup(value = "/rest/document/dataset_create.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testCreate() throws Exception {

        String jsonRequestBody = resources.get("/rest/document/create.json");
        DocumentSaveDTO documentSaveDTO = objectMapper.readValue(jsonRequestBody, DocumentSaveDTO.class);

        List<Client> allClients = clientRepository.findAll();
        assertThat(allClients.size(), is(3));

        List<Document> documents = documentRepository.findAll();
        assertThat(documents.size(), is(0));

        List<Member> members = memberRepository.findAll();
        assertThat(members.size(), is(0));

        List<DocumentMember> documentMembers = documentMemberRepository.findAll();
        assertThat(documentMembers.size(), is(0));

        documentService.create(documentSaveDTO);

        em.flush();

        allClients = clientRepository.findAll();
        assertThat(allClients.size(), is(3));

        User authUser = userService.findByEmail("test@test.com");

        documents = documentRepository.findAll();
        assertThat(documents.size(), is(1));
        Document document = documents.iterator().next();
        assertThat(document.getType(), is(DocumentType.VOUCHER));
        assertThat(document.getClient().getId(), is(documentSaveDTO.getClient().getId()));
        assertThat(document.getIssuedUser().getId(), is(authUser.getId()));
        assertThat(document.getIssuedOrganization().getId(), is(authUser.getOrganization().getId()));
        assertNotNull(document.getNumber());
        assertNotNull(document.getExpiredAt());
        assertNull(document.getPostedUser());
        assertNull(document.getPostedOrganization());
        assertNull(document.getPostedAt());

        documentMembers = documentMemberRepository.findAll();
        assertThat(documentMembers.size(), is(2));

        members = memberRepository.findAll();
        assertThat(members.size(), is(2));
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @DatabaseSetup(value = "/rest/document/dataset_validate.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testValidate() throws Exception {

        Document document = documentRepository.findOne(1L);
        assertThat(document.getPostedUser(), nullValue());
        assertThat(document.getPostedOrganization(), nullValue());
        assertThat(document.getPostedAt(), nullValue());

        documentService.validate(1L);

        em.flush();

        document = documentRepository.findOne(1L);
        User persistentUser = userService.findByEmail("test@test.com");
        assertThat(document.getPostedUser(), is(persistentUser));
        assertThat(document.getPostedOrganization(), is(persistentUser.getOrganization()));
        assertNotNull(document.getPostedAt());
    }
}