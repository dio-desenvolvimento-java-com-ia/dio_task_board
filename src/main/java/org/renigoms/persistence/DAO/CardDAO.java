package org.renigoms.persistence.DAO;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;
import org.renigoms.DTO.CardDTO;
import org.renigoms.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.renigoms.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;

@AllArgsConstructor
public class CardDAO  {

    private final Connection connection;


    public CardEntity insert(CardEntity entity) throws SQLException {
        String sql = "INSERT INTO CARD (title, description, board_column_id) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            int i=1;
            statement.setString(i ++, entity.getTitle());
            statement.setString(i ++, entity.getDescription());
            statement.setLong(i, entity.getColumnEntity().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl)
                entity.setId(impl.getLastInsertID());
        }
        return entity;
    }

    public Void delete(Long id) throws SQLException {
        String sql = "DELETE FROM CARD WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
        }
        return null;
    }

    public Optional<CardDTO> findById(Long id) throws SQLException {
        String sql = """
                    SELECT c.id,
                           c.title,
                           c.description,
                           b.blocked_at,
                           b.block_reason,
                           c.board_column_id,
                           bc.name,
                           (SELECT COUNT(sub_b.id) 
                            FROM BLOCK sub_b 
                            WHERE sub_b.card_id = c.id) block_amount
                    FROM CARD c
                    LEFT JOIN BLOCK b
                        ON c.id = b.card_id
                    AND b.unblocked_at IS NULL
                    INNER JOIN BOARD_COLUMN bc
                        ON bc.id = c.board_column_id
                    WHERE c.id = ?;
                    """;
        BoardColumnDAO boardColumnDAO = new BoardColumnDAO(connection);
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                CardDTO cardDTO = new CardDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        nonNull(resultSet.getString("b.block_reason")),
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("block_amount"),
                        resultSet.getLong("board_column_id"),
                        resultSet.getString("bc.name")

                );
                return Optional.of(cardDTO);
            }

        }
        return Optional.empty();
    }

    public void moveToColumns(Long columnId, Long id) throws SQLException {
        String sql = "UPDATE CARD SET board_column_id = ? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            int i = 1;
            statement.setLong(i ++, columnId);
            statement.setLong(i, id);
            statement.executeUpdate();
        }
    }
}
