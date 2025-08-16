package org.renigoms.interfaces;

import org.renigoms.persistence.entity.BoardColumnEntity;

import java.sql.SQLException;
import java.util.List;

public interface BoardColDAOI {
    BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException;
    List<BoardColumnEntity> findByBoardId(Long id) throws SQLException;
}
