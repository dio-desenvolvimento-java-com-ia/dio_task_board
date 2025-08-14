--liquibase formatted sql
--changeset Renan:202508130812
--comment: block table create

CREATE TABLE BLOCK(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(255) NOT NULL,
    unblocked_at TIMESTAMP NULL,
    unblocked_reason VARCHAR(255) NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT card__block_fk FOREIGN KEY (card_id) REFERENCES CARD(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS