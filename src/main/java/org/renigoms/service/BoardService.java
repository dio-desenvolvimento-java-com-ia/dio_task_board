package org.renigoms.service;

import lombok.AllArgsConstructor;
import org.renigoms.interfaces.GenericMethodsI;
import org.renigoms.persistence.DAO.BoardColumnDAO;
import org.renigoms.persistence.DAO.BoardDAO;
import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BoardService implements GenericMethodsI<BoardEntity, Boolean> {

    private final Connection connection;

    @Override
    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        BoardDAO boardDAO = new BoardDAO(connection);
        Optional<BoardEntity> optional =  boardDAO.findById(id);
        if(optional.isPresent()) {
            BoardEntity entity = optional.get();
            entity.setBoardColumns(boardDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    @Override
    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        BoardDAO BOARDDAO = new BoardDAO(connection);
        BoardColumnDAO boardColumnDAO = new BoardColumnDAO(connection);
        try {
            BOARDDAO.insert(entity);
            List<BoardColumnEntity> colunms = entity.getBoardColumns().stream().map(
                    c -> {
                        c.setBoard(entity);
                        return c;
                    }
            ).toList();
            for (BoardColumnEntity column : colunms){
                boardColumnDAO.insert(column);
            }
            connection.commit();
        }catch(SQLException ex) {
            connection.rollback();
            throw ex;
        }
        return entity;
    }

    @Override
    public Boolean delete(final Long id) throws SQLException {
        BoardDAO BOARDDAO = new BoardDAO(connection);
        try{
            if (!BOARDDAO.exists(id))
                return false;
            BOARDDAO.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
