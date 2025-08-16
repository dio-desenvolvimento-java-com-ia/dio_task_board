package org.renigoms.ui;

import org.renigoms.persistence.config.ConnectionConfig;
import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.BoardColumnKindEnum;
import org.renigoms.persistence.entity.BoardEntity;
import org.renigoms.service.BoardService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
        int option = 0;
        do {
            System.out.println("\n");
            System.out.println("==============================================================");
            System.out.println(MenuMessage.WELCOME_MESSAGE.getValue());
            System.out.println(MenuMessage.MENU_OPTIONS.getValue());
            System.out.println("==============================================================");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.out.println(MenuMessage.EXIT_PROGRAM.getValue());
                default -> System.out.println(MenuMessage.INVALID_OPTION.getValue());
            }
        } while (option != 4);
        System.exit(0);
    }

    private void deleteBoard() {
        System.out.println(MenuMessage.INPUT_DELETE_MESSAGE.getValue());
        long id = scanner.nextLong();
        try (Connection connection = ConnectionConfig.getConnection()) {
            BoardService service = new BoardService(connection);
            if (service.delete(id)) {
                System.out.printf(MenuMessage.BOARD_DELETED.getValue(), id);
                return;
            }
            System.out.printf(MenuMessage.BOARD_NOT_FOUND.getValue(), id);
        } catch (SQLException e) {
            System.out.println(MenuMessage.DELETE_ERROR.getValue());
            System.err.println(e.getMessage());
            execute();
        }
    }

    private void selectBoard() {
        System.out.println(MenuMessage.SELECT_BOARD.getValue());
        Long id = scanner.nextLong();
        try (Connection connection = ConnectionConfig.getConnection()) {
            BoardService service = new BoardService(connection);
            Optional<BoardEntity> board = service.findById(id);
            board.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf(MenuMessage.BOARD_NOT_FOUND.getValue(), id));
        } catch (SQLException e) {
            System.out.println(MenuMessage.SELECT_ERROR.getValue());
            System.err.println(e.getMessage());
            execute();
        }
    }

    private void createBoard() {
        BoardEntity entity = new BoardEntity();
        System.out.println(MenuMessage.BOARD_NAME.getValue());
        entity.setName(scanner.next());
        System.out.println(MenuMessage.COLUMNS_NUMBER.getValue());
        int addColumns = scanner.nextInt();
        List<BoardColumnEntity> columns = new ArrayList<>();
        System.out.println(MenuMessage.COLUMN_INIT.getValue());
        String initialColumnName = scanner.next();
        BoardColumnEntity initialColumn = createColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < addColumns; i++) {
            System.out.println(MenuMessage.COLUMN_PENDING.getValue());
            String pendingColumnName = scanner.next();
            BoardColumnEntity pendingColumn = createColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println(MenuMessage.COLUMN_FINAL.getValue());
        String finalColumnName = scanner.next();
        BoardColumnEntity finalColumn = createColumn(
                finalColumnName, BoardColumnKindEnum.FINAL, addColumns + 1);
        columns.add(finalColumn);

        System.out.println(MenuMessage.COLUMN_CANCEL.getValue());
        String cancelColumnName = scanner.next();
        BoardColumnEntity cancelColumn = createColumn(
                cancelColumnName, BoardColumnKindEnum.CANCEL, addColumns + 2);
        columns.add(cancelColumn);


        entity.setBoardColumns(columns);
        try (Connection connection = ConnectionConfig.getConnection()) {
            BoardService service = new BoardService(connection);
            service.insert(entity);
        } catch (SQLException e) {
            System.out.println(MenuMessage.CREATE_ERROR.getValue());
            System.err.println(e.getMessage());
            execute();
        }


    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int orderIn) {
        BoardColumnEntity entity = new BoardColumnEntity();
        entity.setName(name);
        entity.setKind(kind);
        entity.setOrder(orderIn);
        return entity;
    }
}
