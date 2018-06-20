package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Client;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ClientSaveDTOToDomainMapper extends CustomMapper<ClientSaveDTO, Client> {

    @Override
    public void mapAtoB(ClientSaveDTO clientSaveDTO, Client client, MappingContext context) {

    }
}
