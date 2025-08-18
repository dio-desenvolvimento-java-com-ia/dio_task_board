package org.renigoms.service;

import lombok.AllArgsConstructor;
import org.renigoms.persistence.DAO.BoardColumnDAO;
import org.renigoms.persistence.entity.BoardColumnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardColumnService {

    private final Connection connection;

    public Optional<BoardColumnEntity> findById(final Long id) throws SQLException {
        BoardColumnDAO  dao = new BoardColumnDAO(connection);
        return dao.findById(id);
    }
}
