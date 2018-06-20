package com.inzami.fp.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfiguration {

    @Bean
    @Profile({"test"})
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {

            @Override
            public void migrate(Flyway flyway) {
                flyway.setPlaceholderPrefix("@{");
                flyway.clean();
                flyway.migrate();
            }
        };

        return strategy;
    }

    @Bean
    @Profile("dev")
    public FlywayMigrationStrategy localDevMigrateStrategy() {
        FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {

            @Override
            public void migrate(Flyway flyway) {
                flyway.setPlaceholderPrefix("@{");
                flyway.repair();
                flyway.setLocations("db/dev_migration", "db/migration");
//                flyway.clean();
                flyway.migrate();
            }
        };

        return strategy;
    }

    @Bean
    @Profile({"prod"})
    public FlywayMigrationStrategy prodMigrateStrategy() {
        FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {

            @Override
            public void migrate(Flyway flyway) {
                flyway.setPlaceholderPrefix("@{");
                flyway.migrate();
            }
        };

        return strategy;
    }

}
