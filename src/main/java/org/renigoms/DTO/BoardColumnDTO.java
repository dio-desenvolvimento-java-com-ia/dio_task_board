package org.renigoms.DTO;

import org.renigoms.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id, String name, int orderIn, BoardColumnKindEnum kind, int cardsAmount) {
}
