package com.inzami.fp.rest.mapper;

import com.inzami.fp.domain.Client;
import com.inzami.fp.rest.dto.view.ClientPdfViewDTO;
import com.inzami.fp.rest.dto.view.ClientViewDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ClientToPdfViewDTOMapper extends CustomMapper<Client, ClientPdfViewDTO> {

    @Override
    public void mapAtoB(Client client, ClientPdfViewDTO clientPdfViewDTO, MappingContext context) {
    }
}
