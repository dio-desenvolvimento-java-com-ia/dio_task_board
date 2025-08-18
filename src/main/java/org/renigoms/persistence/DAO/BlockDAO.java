package org.renigoms.persistence.DAO;

import lombok.AllArgsConstructor;
import org.renigoms.interfaces.GenericMethodsI;
import org.renigoms.persistence.config.ConnectionConfig;
import org.renigoms.persistence.entity.BlockEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BlockDAO implements GenericMethodsI<BlockEntity, Void> {

    private final Connection connection;

    @Override
    public BlockEntity insert(BlockEntity entity) throws SQLException {
        String sql = "INSERT INTO block (blocked_at, block_reason, unblocked_at, unblocked_reason) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {


        }
        return null;
    }

    @Override
    public Void delete(Long id) throws SQLException {
        return null;
    }

    @Override
    public Optional<BlockEntity> findById(Long id) throws SQLException {
        return Optional.empty();
    }
}
