package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Client;
import com.inzami.fp.rest.dto.view.ClientInfoDTO;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ClientToInfoDTOMapper extends CustomMapper<Client, ClientInfoDTO> {

    @Override
    public void mapAtoB(Client client, ClientInfoDTO clientInfoDTO, MappingContext context) {
        clientInfoDTO.setInfo(client.getFirstName() + " " + client.getLastName()
                + " (" + client.getBirthDate() + ")");
    }
}
