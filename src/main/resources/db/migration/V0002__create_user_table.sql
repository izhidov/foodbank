-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`                      BIGINT                      AUTO_INCREMENT,
  `organization_id`         BIGINT                      NOT NULL,
  `role`                    VARCHAR(255)                NOT NULL,
  `first_name`              VARCHAR(255)                NOT NULL,
  `last_name`               VARCHAR(255)                NOT NULL,
  `password`                VARCHAR(255)                NOT NULL,
  `email`                   VARCHAR(255)                NOT NULL,
  `phone`                   VARCHAR(255)                NULL,
  `active`                  TINYINT(1)                  NOT NULL DEFAULT 1,
  `created_at`              DATETIME                    NOT NULL DEFAULT NOW(),
  `updated_at`              DATETIME                    NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`),
  UNIQUE KEY (`email`),
  CONSTRAINT `fk_organization_to_user`
  FOREIGN KEY (`organization_id`)
  REFERENCES `organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

INSERT INTO `user`
(`organization_id`, `role`, `first_name`, `last_name`, `password`, `email`, `active`)
VALUES
(1, 'ADMIN', 'admin', 'admin', 1, 'admin@fp.com', 1);
