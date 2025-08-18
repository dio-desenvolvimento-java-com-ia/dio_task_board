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

    MENU_BOARD_OPTIONS("""
            1 - Criar um novo card
            2 - Mover um card
            3 - Bloquear um card
            4 - Desbloquear um card
            5 - Cancelar um card
            6 - Visualizar card
            7 - Visualizar colunas
            8 - Visualizar colunas com cards
            9 - Voltar ao menu anterior um card 
            10 - Sair
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

    COLUMN_NOT_FOUND("Coluna não encontrada !"),

    SELECT_BOARD("Informe o id do board que deseja selecionar"),

    WELCOME_BOARD("Bem vindo ao board %s, selecione a operação desejada.\n"),

    CARD_TITLE("Informe o nome do card"),

    CARD_DESCRIPTION("Dê uma descrição do card"),

    BOARD_DESCRIPTION("Board [%s, %s]\n"),

    COLUMN_DESCRIPTION("Coluna [%s] tipo: [%s] tem %s cards\n"),

    SELECT_BOARD_COLUMN("Escolha uma coluna do board %s\n"),

    CARD_SHOW_DESCRIPTION("Cards %d - %s\nDescrição: %s\n"),

    WELCOME_MESSAGE("Bem vindo ao gerenciador de boards, escolha a opção desejada");

    private final String value;
}
