--liquibase formatted sql
--changeset Renan:20250813080
--comment: cards table create

CREATE TABLE CARD(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT boards_column__card_fk FOREIGN KEY (board_column_id) REFERENCES BOARD_COLUMN(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS