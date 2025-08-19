--liquibase formatted sql
--changeset Renan:202508191153
--comment: set unblocked_reason nullable

ALTER TABLE BLOCK MODIFY unblocked_reason VARCHAR(255) NULL;

--rollback ALTER TABLE BLOCK MODIFY unblocked_reason VARCHAR(255) NULL;