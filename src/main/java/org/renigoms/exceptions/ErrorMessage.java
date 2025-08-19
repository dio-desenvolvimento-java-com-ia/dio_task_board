package org.renigoms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    DELETE_ERROR("Erro inesperado ao deletar o board"),

    CREATE_ERROR("Erro inesperado ao criar o board"),

    SELECT_ERROR("Erro inesperado ao selecionar o board"),

    CARD_NOT_FOUND("Nãp existe um card com o id %s\n"),

    SEARCH_ERROR("Erro ao realizar a busca"),

    CONNECTION_ERROR("Erro ao conectar"),

    CARD_BLOCK_EXCEPTION("O card %s está bloqueado, é necessário desbloqueia-lo"),

    ILLEGAL_STATE_EXCEPTION("O card informado pertence a outro board"),

    CARD_FINISHED_EXCEPTION("O card foi finalizado"),

    CANCELED_CARD("O card está cancelado !"),

    CARD_TYPE_ERROR("O card está em coluna do tipo %s e não pode ser bloqueado"),

    CARD_UNBLOCKED("O card %s não está bloqueado"),

    ENTITY_CARD_ERROR("O card de id %s não foi encontrado");

    private final String message;

    public static void throwExceptions(Connection connection, SQLException ex) throws SQLException {
        connection.rollback();
        throw ex;
    }
}
