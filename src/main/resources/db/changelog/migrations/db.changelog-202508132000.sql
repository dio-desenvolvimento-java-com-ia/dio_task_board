--liquibase formatted sql
--changeset Renan:202508132000
--comment: alter table board_column

ALTER TABLE BOARD_COLUMN
    RENAME COLUMN `order` TO order_in;

--rollback DROP TABLE BOARDS