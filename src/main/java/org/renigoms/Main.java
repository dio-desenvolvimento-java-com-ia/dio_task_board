package org.renigoms;

import org.renigoms.persistence.migration.MigrationStrategy;
import org.renigoms.ui.MainMenu;

import java.sql.Connection;
import java.sql.SQLException;

import static org.renigoms.persistence.config.ConnectionConfig.getConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try (Connection connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new MainMenu().execute();
    }
}