package org.renigoms.persistence.DAO;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;
import org.renigoms.interfaces.GenericMethodsI;
import org.renigoms.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardDAO implements GenericMethodsI<BoardEntity, Void> {

    private final Connection connection;

    @Override
    public BoardEntity insert(BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARD_TABLE (name) VALUES (?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, entity.getName());
            statement.execute();
            if (statement instanceof StatementImpl impl) entity.setId(impl.getLastInsertID());
        }
        return entity;
    }
    @Override
    public Void delete(Long id) throws SQLException{
        String sql = "DELETE FROM BOARD_TABLE WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
        }
        return null;
    }

    @Override
    public Optional<BoardEntity> findById(Long id) throws  SQLException {
        String sql = "SELECT id, name FROM BOARD_TABLE WHERE id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                BoardEntity entity = new BoardEntity();
                entity.setId(id);
                entity.setName(resultSet.getString("name"));
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }

    public boolean exists(final Long id) throws SQLException {
        String sql = "SELECT 1 FROM BOARD_TABLE WHERE id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.execute();
            return statement.getResultSet().next();
        }
    }






}
