package org.renigoms.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuMessage {

    MENU_OPTIONS("""
            1 - Criar um novo Board
            2 - Selecionar um Board existente
            3 - Excluir um Board
            4 - Sair
            """),

    EXIT_PROGRAM("Programa Encerrando..."),

    INPUT_DELETE_MESSAGE("Informe o ID do board que será excluído"),

    INVALID_OPTION("Opção inválida !! Informe uma opção do menu"),

    BOARD_DELETED("O board %s foi excluído\n"),

    BOARD_NOT_FOUND("Não foi encontrado um board com id %s\n"),

    DELETE_ERROR("Erro inesperado ao deletar o board"),

    CREATE_ERROR("Erro inesperado ao criar o board"),

    SELECT_ERROR("Erro inesperado ao selecionar o board"),

    BOARD_NAME("Informe o nome do seu boarn"),

    COLUMNS_NUMBER("Seu board terá colunas além das 3 padrões ? Se sim digite o número se não digite 0."),

    COLUMN_INIT("Informe o nome da coluna inicial do board"),

    COLUMN_PENDING("Informe o nome da coluna pendente do board"),

    COLUMN_FINAL("Informe o nome da coluna final do board"),

    COLUMN_CANCEL("Informe o nome da coluna de cancelamento do board"),

    SELECT_BOARD("Informe o id do board que deseja selecionar"),

    WELCOME_MESSAGE("Bem vindo ao gerenciador de boards, escolha a opção desejada");
    private final String value;


}
