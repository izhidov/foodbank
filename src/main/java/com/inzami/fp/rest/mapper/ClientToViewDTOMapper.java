package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Client;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ClientToViewDTOMapper extends CustomMapper<Client, ClientViewDTO> {

    @Override
    public void mapAtoB(Client client, ClientViewDTO clientViewDTO, MappingContext context) {
    }
}
