package org.renigoms.ui;

import lombok.AllArgsConstructor;
import org.renigoms.persistence.entity.BoardEntity;

import java.util.Scanner;

import static org.renigoms.ui.MenuMessage.*;

@AllArgsConstructor
public class BoardMenu {
    private final BoardEntity board;

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
        int option = 0;
        do {
            System.out.println("\n");
            System.out.println("==============================================================");
            System.out.printf(WELCOME_BOARD.getValue(), board.getId());
            System.out.println(MENU_BOARD_OPTIONS.getValue());
            System.out.println("==============================================================");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCard();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> displayCard();
                case 7 -> displayBoard();
                case 8 -> displayColumnWithCard();
                case 9 -> System.out.println("Voltando ao menu anterior");
                case 10 ->   System.exit(0);
                default -> System.out.println(MenuMessage.INVALID_OPTION.getValue());
            }
        } while (option != 9);
    }

    private void createCard() {
    }

    private void moveCard() {
        
    }

    private void blockCard() {
        
    }

    private void unblockCard() {
        
    }

    private void cancelCard() {
        
    }

    private void displayCard() {
        
    }

    private void displayBoard() {
        
    }

    private void displayColumnWithCard() {
        
    }


}

