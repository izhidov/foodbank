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
        List<Client> clients = clientRepository.findByFirstNameLikeAndLastNameLikeAndSnnLikeAndBirthDateLike(
                clientSearchForm.getFirstName(), clientSearchForm.getLastName(), clientSearchForm.getSsn(), clientSearchForm.getBirthDate(), pageable);
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
        if (StringUtils.isNotBlank(clientUpdateDTO.getAddress2())) {
            client.setAddress2(clientUpdateDTO.getAddress2());
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
        if (StringUtils.isNotBlank(clientUpdateDTO.getEmail())) {
            client.setEmail(clientUpdateDTO.getEmail());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getPhone())) {
            client.setPhone(clientUpdateDTO.getPhone());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getSsn())) {
            client.setSsn(clientUpdateDTO.getSsn());
        }
        if (StringUtils.isNotBlank(clientUpdateDTO.getSpouseSsn())) {
            client.setSpouseSsn(clientUpdateDTO.getSpouseSsn());
        }

        client = clientRepository.save(client);
        return client;
    }

    public Client create(ClientSaveDTO clientSaveDTO) {
        Client client = mapperFacade.map(clientSaveDTO, Client.class);
        save(client);
        return client;
    }

    public List<ClientInfoDTO> autocompleteBySsn(String ssn){
        List<Client> clients = clientRepository.findBySsnStartingWith(ssn, new PageRequest(0, 50));
        List<ClientInfoDTO> clientInfoDTOS = clients.stream().map(client -> {
            ClientInfoDTO clientInfoDTO = new ClientInfoDTO();
            clientInfoDTO.setId(client.getId());
            clientInfoDTO.setSsn(client.getSsn());
            clientInfoDTO.setInfo(client.getFirstName() + " " + client.getLastName() + " " + client.getBirthDate());
            return clientInfoDTO;
        }).collect(Collectors.toList());
        return clientInfoDTOS;
    }
}
