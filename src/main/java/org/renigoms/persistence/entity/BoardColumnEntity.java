package org.renigoms.persistence.entity;

import lombok.Data;

@Data
public class BoardColumnEntity {
    private Long id;
    private String name;
    private BoardColumnKindEnum kind;
    private int order;
    private BoardEntity board = new BoardEntity();
}
