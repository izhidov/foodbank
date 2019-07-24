ALTER TABLE client ADD COLUMN homeless TINYINT(1) not null default 0;
ALTER TABLE client ADD COLUMN gender VARCHAR(255);
ALTER TABLE client ADD COLUMN race VARCHAR(255);
ALTER TABLE client ADD COLUMN marital_status VARCHAR(255);
ALTER TABLE client ADD COLUMN military_status VARCHAR(255);
ALTER TABLE client ADD COLUMN employment VARCHAR(255);
ALTER TABLE client ADD COLUMN government_benefits VARCHAR(255);