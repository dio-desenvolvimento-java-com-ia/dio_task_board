--liquibase formatted sql
--changeset Renan:202508130745
--comment: board_column table create

CREATE TABLE BOARD_COLUMN(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    order_in INT NOT NULL,
    kind VARCHAR(7),
    board_id BIGINT NOT NULL,
    CONSTRAINT boardTB__boards_column_fk FOREIGN KEY (board_id) REFERENCES BOARD_TABLE(id) ON DELETE CASCADE,
    CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board_id, order_in)
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS