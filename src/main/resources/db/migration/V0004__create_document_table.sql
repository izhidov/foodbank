-- -----------------------------------------------------
-- Table `document`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id`                          BIGINT              AUTO_INCREMENT,
  `issued_user_id`              BIGINT              NOT NULL,
  `issued_organization_id`      BIGINT              NOT NULL,
  `posted_user_id`              BIGINT              NULL,
  `posted_organization_id`      BIGINT              NULL,
  `client_id`                   BIGINT              NOT NULL,
  `number`                      VARCHAR(255)        NOT NULL,
  `type`                        VARCHAR(255)        NOT NULL,
  `expired_AT`                  DATETIME            NOT NULL,
  `posted_at`                   DATETIME            NULL,
  `created_at`                  DATETIME            NOT NULL DEFAULT NOW(),
  `updated_at`                  DATETIME            NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  UNIQUE KEY (`number`),
  CONSTRAINT `fk_issued_user_to_document`
  FOREIGN KEY (`issued_user_id`)
  REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posted_user_to_document`
  FOREIGN KEY (`posted_user_id`)
  REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_issued_organization_to_document`
  FOREIGN KEY (`issued_organization_id`)
  REFERENCES `organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posted_organization_to_document`
  FOREIGN KEY (`posted_organization_id`)
  REFERENCES `organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_client_to_document`
  FOREIGN KEY (`client_id`)
  REFERENCES `client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;