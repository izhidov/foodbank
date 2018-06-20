-- -----------------------------------------------------
-- Table `client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id`                      BIGINT                      AUTO_INCREMENT,
  `first_name`              VARCHAR(255)                NOT NULL,
  `last_name`               VARCHAR(255)                NOT NULL,
  `address1`                VARCHAR(255)                NULL,
  `address2`                VARCHAR(255)                NULL,
  `city`                    VARCHAR(255)                NULL,
  `state`                   VARCHAR(255)                NULL,
  `zip`                     VARCHAR(255)                NULL,
  `birth_date`              VARCHAR(255)                NOT NULL,
  `email`                   VARCHAR(255)                NULL,
  `phone`                   VARCHAR(255)                NULL,
  `ssn`                     VARCHAR(255)                NULL,
  `spouse_ssn`              VARCHAR(255)                NULL,
  `created_at`              DATETIME                    NOT NULL DEFAULT NOW(),
  `updated_at`              DATETIME                    NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;