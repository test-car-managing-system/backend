ALTER TABLE `Track`
    ADD COLUMN `weather` VARCHAR(50) NULL COMMENT '현재날씨',
    ADD COLUMN `temperature` INT NULL COMMENT '현재온도';
