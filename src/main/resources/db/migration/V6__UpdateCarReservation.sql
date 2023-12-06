DROP INDEX `IDX_CAR_RESERVATION_RESERVED_AT` ON `CarReservation`;

ALTER TABLE `CarReservation`
    CHANGE COLUMN `reservedAt` `startedAt` DATETIME(6) NOT NULL COMMENT '예약 시작 시각';

ALTER TABLE `CarReservation`
    ADD COLUMN `expiredAt` DATETIME(6) NOT NULL COMMENT '예약 종료 시각';

CREATE INDEX `IDX_CAR_RESERVATION_STARTED_AT` ON `CarReservation` (startedAt);
CREATE INDEX `IDX_CAR_RESERVATION_EXPIRED_AT` ON `CarReservation` (expiredAt);
