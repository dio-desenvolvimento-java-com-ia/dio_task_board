package org.renigoms.ui;

import lombok.AllArgsConstructor;
import org.renigoms.DTO.BoardColumnInfoDTO;
import org.renigoms.DTO.BoardDetailsDTO;
import org.renigoms.persistence.config.ConnectionConfig;
import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.BoardEntity;
import org.renigoms.persistence.entity.CardEntity;
import org.renigoms.service.BoardColumnService;
import org.renigoms.service.BoardService;
import org.renigoms.service.CardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.*;
import static org.renigoms.exceptions.ErrorMessage.*;
import static org.renigoms.ui.MenuMessage.*;

@AllArgsConstructor
public class BoardMenu {
    private final BoardEntity board;

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() {
        int option = 0;
        do {
            out.println("\n");
            out.println("==============================================================");
            out.printf(WELCOME_BOARD.getValue(), board.getId());
            out.println(MENU_BOARD_OPTIONS.getValue());
            out.println("==============================================================");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> displayCard();
                case 7 -> displayBoard();
                case 8 -> displayColumnWithCard();
                case 9 -> out.println("Voltando ao menu anterior");
                case 10 -> exit(0);
                default -> out.println(INVALID_OPTION.getValue());
            }
        } while (option != 9);
    }

    private void createCard() {
        CardEntity card = new CardEntity();
        out.println(CARD_TITLE.getValue());
        card.setTitle(scanner.next());
        out.println(CARD_DESCRIPTION.getValue());
        card.setDescription(scanner.next());
        card.setColumnEntity(board.getInitialColumn());
        try (Connection connection = ConnectionConfig.getConnection()) {
            CardService cardService = new CardService(connection);
            cardService.insert(card);
        } catch (SQLException e) {
            err.println(e.getMessage());
            out.println(CONNECTION_ERROR.getMessage());
        };
    }

    private void moveCardToNextColumn() {
        out.println(CARD_ID.getValue());
        long cardId =  scanner.nextLong();
        try (Connection connection = ConnectionConfig.getConnection()){
            List<BoardColumnInfoDTO> boardColumnEntities = board.getBoardColumns()
                    .stream().map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                    .toList();
            new CardService(connection).moveCardToNextColumn(cardId, boardColumnEntities);
        } catch (SQLException e) {
            err.println(e.getMessage());
            out.println(CONNECTION_ERROR.getMessage());
        }

    }

    private void blockCard() {
        
    }

    private void unblockCard() {
        
    }

    private void cancelCard() {
        out.println(INPUT_DELETE_MESSAGE.getValue().replace("board", "card"));
        long id = scanner.nextLong();
        try (Connection connection = ConnectionConfig.getConnection()){
            CardService service = new CardService(connection);
            service.delete(id);
        } catch (SQLException e) {
            out.println(SEARCH_ERROR.getMessage());
            err.println(e.getMessage());
        }
    }

    private void displayCard() {
        out.println(CARD_ID.getValue());
        Long id = scanner.nextLong();
        try (Connection connection = ConnectionConfig.getConnection()) {
            new CardService(connection).findById(id)
                    .ifPresentOrElse(c -> {
                        out.printf(CARD_SHOW_DESCRIPTION.getValue(), c.id(), c.title(), c.description());
                        out.println(c.blocked() ? "Está bloqueado. Motivo: " + c.blockReason()
                                : "Não está bloqueado");
                        out.printf("Já foi bloqueado %s vezes\n", c.blocksAmount());
                        out.printf("Está no momento na coluna %s - %s\n", c.columnId(), c.columnName());
                    }, () -> out.printf(CARD_NOT_FOUND.getMessage(), id));

        } catch (SQLException e) {
            out.println(SEARCH_ERROR.getMessage());
            err.println(e.getMessage());
        }
    }

    private void displayBoard() {
        try (Connection connection = ConnectionConfig.getConnection()){
            Optional<BoardDetailsDTO> optional = new BoardService(connection)
                    .showBoardDetails(board.getId());
            optional.ifPresent(b -> {
                out.printf(BOARD_DESCRIPTION.getValue(), b.id(), b.name());
                b.columns().forEach(c -> {
                    out.printf(COLUMN_DESCRIPTION.getValue(), c.name(), c.kind(), c.cardsAmount());
                });
            });
        } catch (SQLException e) {
            out.println(SEARCH_ERROR.getMessage());
            err.println(e.getMessage());
        }
    }

    private void displayColumnWithCard() {
        List<Long> columnsIds = board.getBoardColumns()
                .stream().map(BoardColumnEntity::getId).toList();
        long selectedColumn = -1L;
        while (!columnsIds.contains(selectedColumn)){
            out.printf(SELECT_BOARD_COLUMN.getValue(),  board.getName());
            board.getBoardColumns().forEach(c -> out.printf("%s - %s [%s]\n", c.getId(),
                    c.getName(), c.getKind()));
            selectedColumn = scanner.nextLong();
        }
        try (Connection connection = ConnectionConfig.getConnection()){
            Optional<BoardColumnEntity> column = new BoardColumnService(connection).findById(selectedColumn);
            column.ifPresent(co -> {
                out.printf(COLUMN_DESCRIPTION.getValue().substring(0, 22), co.getName(), co.getKind());
                co.getCards().forEach(ca ->
                        out.printf(CARD_SHOW_DESCRIPTION.getValue(),
                                ca.getId(), ca.getTitle(), ca.getDescription()));
            });
        } catch (SQLException e) {
            out.println(SEARCH_ERROR.getMessage());
            err.println(e.getMessage());
        }
    }


}

