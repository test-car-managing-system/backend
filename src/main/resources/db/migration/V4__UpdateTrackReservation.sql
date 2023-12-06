DROP INDEX `IDX_TRACK_RESERVATION_RESERVED_AT` ON `TrackReservation`;

ALTER TABLE `TrackReservation`
    DROP COLUMN `reservedAt`;
