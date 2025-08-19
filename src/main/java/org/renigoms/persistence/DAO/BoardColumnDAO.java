package org.renigoms.persistence.DAO;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.RequiredArgsConstructor;
import org.renigoms.DTO.BoardColumnDTO;
import org.renigoms.interfaces.BoardColDAOI;
import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.BoardColumnKindEnum;
import org.renigoms.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

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
    public List<BoardColumnEntity> findByBoardId(Long boardId) throws SQLException {
        String sql = "SELECT id, name, order_in, kind FROM BOARD_COLUMN WHERE board_id = ? ORDER BY order_in;";
        List<BoardColumnEntity> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
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

    @Override
    public Optional<BoardColumnEntity> findById(Long id) throws SQLException {
        String sql = """
                        SELECT bc.name, 
                               bc.kind,
                               c.id,
                               c.title,
                               c.description
                        FROM BOARD_COLUMN bc
                        LEFT JOIN CARD c
                        ON c.board_column_id = bc.id
                        WHERE bc.id = ?;
                        """;
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                BoardColumnEntity entity = new BoardColumnEntity();
                entity.setName(resultSet.getString("bc.name"));
                entity.setKind(BoardColumnKindEnum.findByName(resultSet.getString("bc.kind")));
                do{
                    if (isNull(resultSet.getString("c.title"))) break;
                    CardEntity cardEntity = new CardEntity();
                    cardEntity.setId(resultSet.getLong("c.id"));
                    cardEntity.setTitle(resultSet.getString("c.title"));
                    cardEntity.setDescription(resultSet.getString("c.description"));
                    entity.getCards().add(cardEntity);
                }while(resultSet.next());
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BoardColumnDTO> findByIdWithDetails(Long boardId) throws SQLException {
        String sql = """
                     SELECT bc.id, 
                            bc.name,                      
                            bc.kind,
                            (SELECT COUNT(c.id)
                                     FROM CARD c 
                                     WHERE c.board_column_id = bc.id) cards_amount
                     FROM BOARD_COLUMN bc
                     WHERE board_id = ? 
                     ORDER BY order_in;
                     """;
        List<BoardColumnDTO> dtos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                BoardColumnDTO entity = new BoardColumnDTO(
                        resultSet.getLong("bc.id"),
                        resultSet.getString("bc.name"),
                        resultSet.getInt("bc.order_in"),
                        BoardColumnKindEnum.findByName(resultSet.getString("bc.kind")),
                        resultSet.getInt("cards_amount")
                );
                dtos.add(entity);
            }
            return dtos;
        }
    }
}
