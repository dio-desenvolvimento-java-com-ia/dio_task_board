package org.renigoms.DTO;

import java.time.OffsetDateTime;

public record CardDTO(
        boolean blocked,
        OffsetDateTime blockedAt,
        String blockReason,
        int blocksAmount,
        Long columnId,
        String columnName
) {
}
