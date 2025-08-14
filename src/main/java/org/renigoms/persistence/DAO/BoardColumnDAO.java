package org.renigoms.persistence.DAO;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.RequiredArgsConstructor;
import org.renigoms.interfaces.GenericMethodsI;
import org.renigoms.persistence.entity.BoardColumnEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardColumnDAO implements GenericMethodsI<BoardColumnEntity, Void> {

    private final Connection connection;

    @Override
    public BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARD_COLUMN (name, order_in, kind, board_id) VALUES (?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getOrder());
            statement.setString(3, entity.getKind().name());
            statement.setLong(4, entity.getBoard().getId());
            statement.execute();
            if (statement instanceof StatementImpl imp)
                entity.setId(imp.getLastInsertID());
            return entity;
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    @Override
    public Void delete(Long id) throws SQLException {
        String sql = "DELETE FROM BOARD_COLUMN WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
        return null;
    }

    @Override
    public Optional<BoardColumnEntity> findById(Long id) throws SQLException {
        return Optional.empty();
    }
}
