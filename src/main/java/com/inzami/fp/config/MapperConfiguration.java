package com.inzami.fp.config;

import com.inzami.fp.domain.*;
import com.inzami.fp.rest.dto.save.ClientSaveDTO;
import com.inzami.fp.rest.dto.save.OrganizationSaveDTO;
import com.inzami.fp.rest.dto.save.UserSaveDTO;
import com.inzami.fp.rest.dto.view.*;
import com.inzami.fp.rest.mapper.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class MapperConfiguration {

    @Autowired
    private ClientToViewDTOMapper clientToViewDTOMapper;
    @Autowired
    private DocumentToLightViewDTOMapper documentToLightViewDTOMapper;
    @Autowired
    private ClientSaveDTOToDomainMapper clientSaveDTOToDomainMapper;
    @Autowired
    private MemberToViewDTOMapper memberToViewDTOMapper;
    @Autowired
    private DocumentToViewDTOMapper documentToViewDTOMapper;
    @Autowired
    private DocumentMemberToMemberViewDTOMapper documentMemberToMemberViewDTOMapper;
    @Autowired
    private DocumentToPdfViewDTOMapper documentToPdfViewDTOMapper;
    @Autowired
    private OrganizationToViewDTOMapper organizationToViewDTOMapper;
    @Autowired
    private OrganizationSaveDTOToDomainMapper organizationSaveDTOToDomainMapper;
    @Autowired
    private UserSaveDTOToDomainMapper userSaveDTOToDomainMapper;
    @Autowired
    private UserToViewDTOMapper userToViewDTOMapper;
    @Autowired
    private ClientToInfoDTOMapper clientToInfoDTOMapper;

    @Bean
    public MapperFacade mapperFacade() {
        return getFactory().getMapperFacade();
    }

    private MapperFactory getFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();

        mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(ZonedDateTime.class));

        //CLIENT
        mapperFactory.classMap(Client.class, ClientViewDTO.class)
                .byDefault()
                .customize(clientToViewDTOMapper)
                .register();
        mapperFactory.classMap(ClientSaveDTO.class, Client.class)
                .byDefault()
                .customize(clientSaveDTOToDomainMapper)
                .register();
        mapperFactory.classMap(Client.class, ClientInfoDTO.class)
                .byDefault()
                .customize(clientToInfoDTOMapper)
                .register();

        //DOCUMENT MEMBER
        mapperFactory.classMap(DocumentMember.class, MemberViewDTO.class)
                .byDefault()
                .customize(documentMemberToMemberViewDTOMapper)
                .register();

        //MEMBER
        mapperFactory.classMap(Member.class, MemberViewDTO.class)
                .byDefault()
                .customize(memberToViewDTOMapper)
                .register();

        //DOCUMENT
        mapperFactory.classMap(Document.class, DocumentLightViewDTO.class)
                .byDefault()
                .customize(documentToLightViewDTOMapper)
                .register();
        mapperFactory.classMap(Document.class, DocumentViewDTO.class)
                .byDefault()
                .customize(documentToViewDTOMapper)
                .register();
        mapperFactory.classMap(Document.class, DocumentPdfViewDTO.class)
                .byDefault()
                .customize(documentToPdfViewDTOMapper)
                .register();

        //ORGANIZATION
        mapperFactory.classMap(Organization.class, OrganizationViewDTO.class)
                .byDefault()
                .customize(organizationToViewDTOMapper)
                .register();
        mapperFactory.classMap(OrganizationSaveDTO.class, Organization.class)
                .byDefault()
                .customize(organizationSaveDTOToDomainMapper)
                .register();

        //USER
        mapperFactory.classMap(User.class, UserViewDTO.class)
                .byDefault()
                .customize(userToViewDTOMapper)
                .register();
        mapperFactory.classMap(UserSaveDTO.class, User.class)
                .byDefault()
                .customize(userSaveDTOToDomainMapper)
                .register();

        return mapperFactory;
    }
}
