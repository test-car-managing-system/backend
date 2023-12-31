ALTER TABLE `GasStationHistory`
    MODIFY COLUMN `carStockId` BIGINT NULL COMMENT '차량 재고 ID';

ALTER TABLE `GasStationHistory`
    ADD COLUMN `updateMemberId` BIGINT NOT NULL COMMENT '수정인 ID';

ALTER TABLE `GasStationHistory`
    ADD CONSTRAINT FK_GAS_STATION_HISTORY_UPDATE_MEMBER_ID FOREIGN KEY (updateMemberId) REFERENCES Member(id);