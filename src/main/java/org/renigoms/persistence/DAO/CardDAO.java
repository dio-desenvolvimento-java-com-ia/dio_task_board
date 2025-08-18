package org.renigoms.persistence.DAO;

import lombok.AllArgsConstructor;
import org.renigoms.interfaces.GenericMethodsI;
import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardDAO implements GenericMethodsI<CardEntity, Void> {

    private final Connection connection;

    @Override
    public CardEntity insert(CardEntity entity) throws SQLException {
        String sql = "INSERT INTO CARD (title, description, board_column_id) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getColumnEntity().getId());
            statement.execute();
        }
        return entity;
    }

    @Override
    public Void delete(Long id) throws SQLException {
        String sql = "DELETE FROM CARD WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
        }
        return null;
    }

    @Override
    public Optional<CardEntity> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM CARD WHERE id = ?;";
        BoardColumnDAO boardColumnDAO = new BoardColumnDAO(connection);
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                CardEntity entity = new CardEntity();
                entity.setId(id);
                entity.setTitle(resultSet.getString("title"));
                entity.setDescription(resultSet.getString("description"));
                entity.setColumnEntity(boardColumnDAO
                        .findById(resultSet.getLong("board_colum_id"))
                        .orElse(new BoardColumnEntity()));
                return Optional.of(entity);
            }

        }
        return Optional.empty();
    }
}
