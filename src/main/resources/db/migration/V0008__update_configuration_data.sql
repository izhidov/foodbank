DELETE FROM `configuration` WHERE `key` = 'visit_interval_limit_days';
INSERT INTO `configuration` (`key`, `value`) VALUES ('visit_interval_limit_weeks', '4');