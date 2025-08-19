package org.renigoms.persistence.DAO;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static org.renigoms.exceptions.ErrorMessage.throwExceptions;
import static org.renigoms.persistence.converter.OffsetDateTimeConverter.toTimestamp;

@AllArgsConstructor
public class BlockDAO {

    private final Connection connection;

    private void executeOperation(String sql, String reason, Long cardId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            statement.setTimestamp(i ++, toTimestamp(OffsetDateTime.now()));
            statement.setString(i ++, reason);
            statement.setLong(i, cardId);
            statement.execute();
        } catch (SQLException e) {
            throwExceptions(connection, e);
        }
    }

    public void block(Long cardId, String reason) throws SQLException {
        String sql = "INSERT INTO BLOCK (blocked_at, block_reason, card_id) VALUES (?,?,?);";
        executeOperation(sql, reason, cardId);
    }

    public void unblock(Long cardId, String reason) throws SQLException {
        String sql = """
                UPDATE BLOCK 
                SET unblocked_at = ?, 
                unblocked_reason = ? 
                WHERE card_id = ? 
                AND unblocked_reason IS NULL;
                """;
       executeOperation(sql, reason, cardId);
    }
}
