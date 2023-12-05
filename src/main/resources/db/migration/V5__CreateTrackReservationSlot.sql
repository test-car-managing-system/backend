CREATE TABLE `TrackReservationSlot` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '시험장 예약 슬롯 ID',
    `trackId` BIGINT(20) NOT NULL COMMENT '시험장 ID',
    `trackReservationId` BIGINT(20) NOT NULL COMMENT '시험장 예약 ID',
    `startedAt` DATETIME(6) NOT NULL COMMENT '예약 시작 시각',
    `expiredAt` DATETIME(6) NOT NULL COMMENT '예약 종료 시각',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_TRACK_RESERVATION_SLOT PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `TrackReservation`
    ADD CONSTRAINT FK_TRACK_RESERVATION_SLOT_TRACK_ID FOREIGN KEY (trackId) REFERENCES Track(id),
    ADD CONSTRAINT FK_TRACK_RESERVATION_SLOT_TRACK_RESERVATION_ID FOREIGN KEY (trackReservationId) REFERENCES TrackReservation(id);

CREATE INDEX IDX_TRACK_RESERVATION_STARTED_AT ON `TrackReservationSlot` (startedAt);
CREATE INDEX IDX_TRACK_RESERVATION_EXPIRED_AT ON `TrackReservationSlot` (expiredAt);
