-- -----------------------------------------------------
-- Table `member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id`                      BIGINT                      AUTO_INCREMENT,
  `client_id`               BIGINT                      NOT NULL,
  `first_name`              VARCHAR(255)                NOT NULL,
  `last_name`               VARCHAR(255)                NOT NULL,
  `birth_date`              VARCHAR(255)                NOT NULL,
  `created_at`              DATETIME                    NOT NULL DEFAULT NOW(),
  `updated_at`              DATETIME                    NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_client_to_member`
  FOREIGN KEY (`client_id`)
  REFERENCES `client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;