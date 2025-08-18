package org.renigoms.interfaces;

import org.renigoms.DTO.BoardColumnDTO;
import org.renigoms.persistence.entity.BoardColumnEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BoardColDAOI {
    BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException;
    List<BoardColumnEntity> findByBoardId(Long id) throws SQLException;
    Optional<BoardColumnEntity> findById(Long id) throws SQLException;
    List<BoardColumnDTO> findByIdWithDetails(Long id) throws SQLException;
}
