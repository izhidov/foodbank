package com.inzami.fp.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.inzami.fp.FpApplication;
import com.inzami.fp.domain.Client;
import com.inzami.fp.repository.ClientRepository;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rules.ResourceRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@Transactional
public class ClientControllerTest {

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
    @DatabaseSetup(value = "/rest/client/dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testAutocompleteFirstName() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/client/autocomplete/firstname")
                .param("firstName", "to"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        em.flush();

        JSONAssert.assertEquals(
                "{\"response\" : [ \"tom\", \"tomas\" ]\n" + "}",
                result,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @DatabaseSetup(value = "/rest/client/dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testAutocompleteLastName() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/client/autocomplete/lastname")
                .param("lastName", "pe"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        em.flush();

        JSONAssert.assertEquals(
                "{\"response\" : [ \"pet\", \"peter\" ]\n" + "}",
                result,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @DatabaseSetup(value = "/rest/client/dataset_get_by_id.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testGetById() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/client/1"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        em.flush();

        JSONAssert.assertEquals(
                resources.get("/rest/client/get_by_id_result.json"),
                result,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @DatabaseSetup(value = "/rest/client/dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testUpdate() throws Exception {

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/client/1")
                .content(resources.get("/rest/client/update.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        em.flush();

        JSONAssert.assertEquals(
                resources.get("/rest/client/update_result.json"),
                result,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @WithUserDetails(value = "test@test.com")
    @DatabaseSetup(value = "/rest/client/dataset.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testCreate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/client")
                .content(resources.get("/rest/client/create.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        em.flush();

        List<Client> clients = clientRepository.findByFirstNameLikeAndLastNameLikeAndSnnLikeAndBirthDateLike("1", "1", "1", "22.22.2222", new PageRequest(0, 10));
        Client client = clients.iterator().next();

        ClientSaveDTO clientSaveDTO = objectMapper.readValue(resources.get("/rest/client/create.json"), ClientSaveDTO.class);
        assertThat(client.getFirstName(), is(clientSaveDTO.getFirstName()));
        assertThat(client.getLastName(), is(clientSaveDTO.getLastName()));
        assertThat(client.getBirthDate(), is(clientSaveDTO.getBirthDate()));
        assertThat(client.getAddress1(), is(clientSaveDTO.getAddress1()));
        assertThat(client.getAddress2(), is(clientSaveDTO.getAddress2()));
        assertThat(client.getState(), is(clientSaveDTO.getSpouseSsn()));
        assertThat(client.getCity(), is(clientSaveDTO.getCity()));
        assertThat(client.getZip(), is(clientSaveDTO.getZip()));
        assertThat(client.getEmail(), is(clientSaveDTO.getEmail()));
        assertThat(client.getPhone(), is(clientSaveDTO.getPhone()));
        assertThat(client.getSsn(), is(clientSaveDTO.getSsn()));
        assertThat(client.getSpouseSsn(), is(clientSaveDTO.getSpouseSsn()));
    }
}
