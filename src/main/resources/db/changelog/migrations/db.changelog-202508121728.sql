--liquibase formatted sql
--changeset Renan:202508121728
--comment: board table create

CREATE TABLE BOARD_TABLE(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS