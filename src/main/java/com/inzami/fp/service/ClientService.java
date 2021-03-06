package com.inzami.fp.service;

import com.inzami.fp.domain.Client;
import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.repository.ClientRepository;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rest.dto.search.ClientSearchForm;
import com.inzami.fp.rest.dto.update.ClientUpdateDTO;
import com.inzami.fp.rest.dto.view.ClientInfoDTO;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MapperFacade mapperFacade;

    public Client findOne(Long id) throws EntityNotFoundInServiceException {
        Client result = clientRepository.findOne(id);
        if (result == null) {
            throw new EntityNotFoundInServiceException(Client.class, id);
        }
        return result;
    }

    public Client findOneNoException(Long id) throws EntityNotFoundInServiceException {
        Client result = clientRepository.findOne(id);
        return result;
    }

    public List<Client> searchClients(ClientSearchForm clientSearchForm) {
        Pageable pageable = new PageRequest(clientSearchForm.getPage(), clientSearchForm.getLimit());
        List<Client> clients = clientRepository.findByFirstNameLikeAndLastNameLikeAndBirthDateLike(
                clientSearchForm.getFirstName(), clientSearchForm.getLastName(), clientSearchForm.getBirthDate(), pageable);
        return clients;
    }

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public Client saveAndFlush(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    public Client update(Long clientId, ClientUpdateDTO clientUpdateDTO) {
        Client client = findOne(clientId);
        if (StringUtils.isNotBlank(clientUpdateDTO.getFirstName())) {
            client.setFirstName(clientUpdateDTO.getFirstName());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getLastName())) {
            client.setLastName(clientUpdateDTO.getLastName());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getAddress1())) {
            client.setAddress1(clientUpdateDTO.getAddress1());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getCity())) {
            client.setCity(clientUpdateDTO.getCity());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getState())) {
            client.setState(clientUpdateDTO.getState());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getZip())) {
            client.setZip(clientUpdateDTO.getZip());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getBirthDate())) {
            client.setBirthDate(clientUpdateDTO.getBirthDate());
        }
        client.setAddress2(clientUpdateDTO.getAddress2());
        client.setEmail(clientUpdateDTO.getEmail());
        client.setPhone(clientUpdateDTO.getPhone());

        client.setHomeless("on".equals(clientUpdateDTO.getHomeless()));

        if (!clientUpdateDTO.getGender().equals("0")) {
            client.setGender(clientUpdateDTO.getGender());
        }
        if (!clientUpdateDTO.getGovernmentBenefits().equals("0")) {
            client.setGovernmentBenefits(clientUpdateDTO.getGovernmentBenefits());
        }
        if (!clientUpdateDTO.getMaritalStatus().equals("0")) {
            client.setMaritalStatus(clientUpdateDTO.getMaritalStatus());
        }
        if (!clientUpdateDTO.getMilitaryStatus().equals("0")) {
            client.setMilitaryStatus(clientUpdateDTO.getMilitaryStatus());
        }
        if (!clientUpdateDTO.getEmployment().equals("0")) {
            client.setEmployment(clientUpdateDTO.getEmployment());
        }
        if (!clientUpdateDTO.getRace().equals("0")) {
            client.setRace(clientUpdateDTO.getRace());
            if(clientUpdateDTO.getRace().equals("OTHER")){
                client.setOtherRace(clientUpdateDTO.getOtherRace());
            } else {
                client.setOtherRace(null);
            }
        }

        client = clientRepository.save(client);
        return client;
    }

    public Client create(ClientSaveDTO clientSaveDTO) {
        Client client = mapperFacade.map(clientSaveDTO, Client.class);
        save(client);
        return client;
    }
}
