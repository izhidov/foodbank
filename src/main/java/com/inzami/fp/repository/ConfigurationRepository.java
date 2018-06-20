package com.inzami.fp.repository;

import com.inzami.fp.domain.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    Configuration findOneByKey(String applicationName, String applicationVersion, String key);
}
