CREATE TABLE `Expense` (
    `id`	            BIGINT	        NOT NULL	AUTO_INCREMENT COMMENT '지출내역 id',
    `memberId`  	    BIGINT	        NOT NULL	COMMENT '등록한 사람 id',
    `updateMemberId`  	BIGINT	        NOT NULL	COMMENT '수정한 사람 id',
    `carStockId`        BIGINT	        NULL	COMMENT '차량재고 id',
    `amount`	        BIGINT	        NOT NULL	COMMENT '금액',
    `description`	    TEXT	        NOT NULL	COMMENT '지출내용',
    `usedAt`            DATETIME(6)	    NOT NULL	COMMENT '지출일시',
    `createdAt`	        DATETIME(6)	    NOT NULL	COMMENT '생성일시',
    `updatedAt`	        DATETIME(6)	    NOT NULL	COMMENT '수정일시',
    `deleted`           BIT(1)          NOT NULL    COMMENT '삭제 여부',
    CONSTRAINT PK_EXPENSE PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `Expense`
    ADD CONSTRAINT FK_EXPENSE_MEMBER_ID FOREIGN KEY (memberId) REFERENCES Member(id),
    ADD CONSTRAINT FK_EXPENSE_UPDATE_MEMBER_ID FOREIGN KEY (updateMemberId) REFERENCES Member(id),
    ADD CONSTRAINT FK_EXPENSE_CAR_STOCK_ID FOREIGN KEY (carStockId) REFERENCES CarStock(id);

CREATE INDEX IDX_EXPENSE_USED_AT ON `GasStationHistory` (usedAt);
