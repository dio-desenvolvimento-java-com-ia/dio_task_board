package org.renigoms.persistence.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.renigoms.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class MigrationStrategy {

    private final Connection connection;

    public void executeMigration(){
        PrintStream originalOut = System.out;
        PrintStream originalError = System.err;

        try(FileOutputStream fos = new FileOutputStream("liquibase.log")){
            System.setOut(new PrintStream(fos));
            System.setErr(new PrintStream(fos));
            try(Connection conn = getConnection();
                JdbcConnection jdbcConnection = new JdbcConnection(connection);
            ) {

                Liquibase liquibase = new Liquibase(
                        "/db/changelog/db.changelog-master.yml",
                        new ClassLoaderResourceAccessor(),jdbcConnection);
                liquibase.update();
            } catch (SQLException e) {
               e.printStackTrace();
            } catch (DatabaseException e) {
                e.printStackTrace();
            } catch (LiquibaseException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.setOut(originalOut);
            System.setErr(originalError);
        }

    }
}
