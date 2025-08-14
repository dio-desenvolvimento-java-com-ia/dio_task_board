package org.renigoms.service;

import lombok.AllArgsConstructor;
import org.renigoms.persistence.DAO.BoardDAO;
import org.renigoms.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

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
}
