-- -----------------------------------------------------
-- Table `configuration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `id`                          BIGINT              AUTO_INCREMENT,
  `key`                         VARCHAR(255)               NOT NULL,
  `value`                       VARCHAR(255)               NOT NULL,
  `created_at`                  DATETIME            NOT NULL DEFAULT NOW(),
  `updated_at`                  DATETIME            NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

INSERT INTO `configuration` (`key`, `value`) VALUES ('voucher_valid_for_days', '2');
INSERT INTO `configuration` (`key`, `value`) VALUES ('visit_interval_limit_days', '56');