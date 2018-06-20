-- -----------------------------------------------------
-- Table `document_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `document_member`;
CREATE TABLE `document_member` (
  `id`                          BIGINT              AUTO_INCREMENT,
  `document_id`                 BIGINT              NOT NULL,
  `client_id`                   BIGINT              NOT NULL,
  `member_id`                   BIGINT              NOT NULL,
  `created_at`                  DATETIME            NOT NULL DEFAULT NOW(),
  `updated_at`                  DATETIME            NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_document_to_document_member`
  FOREIGN KEY (`document_id`)
  REFERENCES `document` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_client_to_document_member`
  FOREIGN KEY (`client_id`)
  REFERENCES `client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_member_to_document_member`
  FOREIGN KEY (`member_id`)
  REFERENCES `member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;