package com.inzami.fp.config;

import com.inzami.fp.domain.Configuration;
import com.inzami.fp.exception.PropertyNotFoundException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationProperties {

    public static final String VOUCHER_VALID_FOR_DAYS = "voucher_valid_for_days";
    public static final String VISIT_INTERVAL_LIMIT_WEEKS = "visit_interval_limit_weeks";

    public static Map<String, String> properties;

    public static void loadProperties(Collection<Configuration> configurations) {
        properties = configurations.stream().collect(Collectors.toMap(Configuration::getKey, Configuration::getValue));
    }

    public static String getProperty(String name) {
        String value = properties.get(name);
        if (value == null) {
            throw new PropertyNotFoundException(ConfigurationProperties.class, name);
        }
        return value;
    }

    public static Integer getIntegerProperty(String name) {
        String value = properties.get(name);
        if (value == null) {
            throw new PropertyNotFoundException(ConfigurationProperties.class, name);
        }
        return Integer.parseInt(value);
    }

    public static BigDecimal getBigDecimalProperty(String name) {
        String value = properties.get(name);
        if (value == null) {
            throw new PropertyNotFoundException(ConfigurationProperties.class, name);
        }
        return new BigDecimal(value);
    }

    public static Boolean getBooleanProperty(String name) {
        String value = properties.get(name);
        if (value == null) {
            throw new PropertyNotFoundException(ConfigurationProperties.class, name);
        }
        return Boolean.valueOf(value);
    }
}
