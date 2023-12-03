CREATE TABLE `Member` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '사용자 id',
    `departmentId`	BIGINT	        NOT NULL	COMMENT '부서 id',
    `email`	        VARCHAR(50)	    NOT NULL	COMMENT '이메일(계정)',
    `password`	    VARCHAR(50)	    NOT NULL	COMMENT '패스워드',
    `name`	        VARCHAR(20)	    NOT NULL	COMMENT '사용자 이름',
    `role`	        VARCHAR(10)	    NOT NULL	COMMENT '권한',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_MEMBER PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Department` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '부서 id',
    `name`	        VARCHAR(20)	    NOT NULL	COMMENT '부서명',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_DEPARTMENT PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Car` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '차량 id',
    `name`	        VARCHAR(50)	    NOT NULL	COMMENT '차량명',
    `releasedAt`	DATETIME(6)	    NOT NULL	COMMENT '출시일자',
    `type`	        VARCHAR(10)	    NOT NULL	COMMENT '처종',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_MEMBER PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CarStock` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '차량재고 id',
    `carId`	        BIGINT	        NOT NULL	COMMENT '차량 id',
    `stockNumber`	VARCHAR(12)	    NOT NULL	COMMENT '재고번호',
    `status`	    VARCHAR(10)	    NOT NULL	COMMENT '재고상태',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_CAR_STOCK PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CarReservation` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '시험차량 대여 id',
    `memberId`	    BIGINT	        NOT NULL	COMMENT '사용자 id',
    `carStockId`	BIGINT	        NOT NULL	COMMENT '차량재고 id',
    `reservedAt`	DATETIME(6)	    NOT NULL	COMMENT '대여시각',
    `status`	    VARCHAR(10)	    NOT NULL	COMMENT '대여상태',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_CAR_RESERVATION PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Track` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '시험장 id',
    `name`	        VARCHAR(50)	    NOT NULL	COMMENT '시험장 이름',
    `location`	    VARCHAR(255)	NOT NULL	COMMENT '위치',
    `latitude`	    DOUBLE	            NULL	COMMENT '위도',
    `longitude`	    DOUBLE	            NULL	COMMENT '경도',
    `description`	TEXT	        NOT NULL	COMMENT '시험장 특성',
    `length`	    DOUBLE	        NOT NULL	COMMENT '시험장 길이',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_TRACK PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `TrackReservation` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '주행시험장 대여 id',
    `memberId`	    BIGINT	        NOT NULL	COMMENT '사용자 id',
    `trackId`	    BIGINT	        NOT NULL	COMMENT '시험장 id',
    `reservedAt`	DATETIME(6)	    NOT NULL	COMMENT '예약시각',
    `status`	    VARCHAR(10)	    NOT NULL	COMMENT '대여상태',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_TRACK_RESERVATION PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `CarTest` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '주행시험 이력 id',
    `memberId`	    BIGINT	        NOT NULL	COMMENT '사용자 id',
    `trackId`	    BIGINT	        NOT NULL	COMMENT '시험장 id',
    `carStockId`	BIGINT	        NOT NULL	COMMENT '차량재고 id',
    `performedAt`	DATETIME(6)	    NOT NULL	COMMENT '수행시간',
    `result`	    TEXT    	    NOT NULL	COMMENT '수행결과',
    `memo`	        TEXT    	        NULL	COMMENT '비고',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_CAR_TEST PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `GasStation` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '주유소 id',
    `name`	        VARCHAR(50)	    NOT NULL	COMMENT '이름',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_GAS_STATION PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `GasStationHistory` (
    `id`	        BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '주유소 이용 id',
    `memberId`	    BIGINT	        NOT NULL	COMMENT '사용자 id',
    `gasStationId`	BIGINT	        NOT NULL	COMMENT '주유소 id',
    `carStockId`	BIGINT	        NOT NULL	COMMENT '차량재고 id',
    `amount`	    DOUBLE	        NOT NULL	COMMENT '주유량',
    `usedAt`	    DATETIME(6)	    NOT NULL	COMMENT '이용시각',
    `createdAt`	    DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	    DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`       BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_GAS_STATION_HISTORY PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `Member`
    ADD CONSTRAINT FK_MEMBER_DEPARTMENT_ID FOREIGN KEY (departmentId) REFERENCES Department(id);

ALTER TABLE `CarStock`
    ADD CONSTRAINT FK_CAR_STOCK_CAR_ID FOREIGN KEY (carId) REFERENCES Car(id);

ALTER TABLE `CarReservation`
    ADD CONSTRAINT FK_CAR_RESERVATION_MEMBER_ID FOREIGN KEY (memberId) REFERENCES Member(id),
    ADD CONSTRAINT FK_CAR_RESERVATION_CAR_STOCK_ID FOREIGN KEY (carStockId) REFERENCES CarStock(id);

ALTER TABLE `TrackReservation`
    ADD CONSTRAINT FK_TRACK_RESERVATION_MEMBER_ID FOREIGN KEY (memberId) REFERENCES Member(id),
    ADD CONSTRAINT FK_TRACK_RESERVATION_TRACK_ID FOREIGN KEY (trackId) REFERENCES Track(id);

ALTER TABLE `CarTest`
    ADD CONSTRAINT FK_CAR_TEST_MEMBER_ID FOREIGN KEY (memberId) REFERENCES Member(id),
    ADD CONSTRAINT FK_CAR_TEST_TRACK_ID FOREIGN KEY (trackId) REFERENCES Track(id),
    ADD CONSTRAINT FK_CAR_TEST_CAR_STOCK_ID FOREIGN KEY (carStockId) REFERENCES CarStock(id);

ALTER TABLE `GasStationHistory`
    ADD CONSTRAINT FK_GAS_STATION_HISTORY_MEMBER_ID FOREIGN KEY (memberId) REFERENCES Member(id),
    ADD CONSTRAINT FK_GAS_STATION_HISTORY_GAS_STATION_ID FOREIGN KEY (gasStationId) REFERENCES GasStation(id),
    ADD CONSTRAINT FK_GAS_STATION_HISTORY_CAR_STOCK_ID FOREIGN KEY (carStockId) REFERENCES CarStock(id);

CREATE INDEX IDX_MEMBER_CREATED_AT ON `Member` (createdAt);
CREATE INDEX IDX_CAR_RELEASED_AT ON `Car` (releasedAt);
CREATE INDEX IDX_CAR_RESERVATION_RESERVED_AT ON `CarReservation` (reservedAt);
CREATE INDEX IDX_TRACK_RESERVATION_RESERVED_AT ON `TrackReservation` (reservedAt);
CREATE INDEX IDX_CAR_TEST_PERFORMED_AT ON `CarTest` (performedAt);
CREATE INDEX IDX_GAS_STATION_HISTORY_USED_AT ON `GasStationHistory` (usedAt);
