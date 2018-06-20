package com.inzami.fp.service;

import com.inzami.fp.config.ConfigurationProperties;
import com.inzami.fp.domain.Configuration;
import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.repository.ConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository repository;

    public Configuration findOne(Long id) throws EntityNotFoundInServiceException {
        Configuration result = repository.findOne(id);
        if (result == null) {
            throw new EntityNotFoundInServiceException(Configuration.class, id);
        }
        return result;
    }

    public void loadConfigurationFromDb() throws EntityNotFoundInServiceException {
        List<Configuration> configuration = repository.findAll();
        ConfigurationProperties.loadProperties(configuration);
    }
}
