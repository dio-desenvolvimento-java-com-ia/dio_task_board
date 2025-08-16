package org.renigoms.persistence.DAO;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.RequiredArgsConstructor;
import org.renigoms.interfaces.BoardColDAOI;
import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.BoardColumnKindEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BoardColumnDAO implements BoardColDAOI {

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
    public List<BoardColumnEntity> findByBoardId(Long id) throws SQLException {
        String sql = "SELECT id, name, order_in, kind FROM BOARD_COLUMN WHERE board_id = ? ORDER BY order_in;";
        List<BoardColumnEntity> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                BoardColumnEntity entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order_in"));
                entity.setKind(BoardColumnKindEnum.findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;
        }
    }
}
