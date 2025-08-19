package org.renigoms.DTO;

import org.renigoms.persistence.entity.BoardColumnEntity;
import org.renigoms.persistence.entity.BoardColumnKindEnum;

import java.util.List;

public record BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {

    public static List<BoardColumnInfoDTO> toBoardColumnInfo(List<BoardColumnEntity> boardColumnEntities) {
        return boardColumnEntities
                .stream()
                .map(bc -> new BoardColumnInfoDTO(
                        bc.getId(),
                        bc.getOrder(),
                        bc.getKind()
                )).toList();
    }
    
}
