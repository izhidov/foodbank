-- -----------------------------------------------------
-- Table `organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id`                      BIGINT                              AUTO_INCREMENT,
  `type`                    VARCHAR(255)                        NOT NULL,
  `name`                    VARCHAR(255)                        NOT NULL,
  `city`                    VARCHAR(255)                        NULL,
  `state`                   VARCHAR(255)                        NULL,
  `zip`                     VARCHAR(255)                        NULL,
  `address1`                VARCHAR(255)                        NULL,
  `address2`                VARCHAR(255)                        NULL,
  `email`                   VARCHAR(255)                        NULL,
  `phone`                   VARCHAR(255)                        NULL,
  `active`                  TINYINT(1)                          NOT NULL DEFAULT 1,
  `created_at`              DATETIME                            NOT NULL DEFAULT NOW(),
  `updated_at`              DATETIME                            NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

INSERT INTO organization (type, name) VALUES ('BASE', 'Unknown organization');