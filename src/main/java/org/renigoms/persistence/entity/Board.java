package org.renigoms.persistence.entity;

import java.util.List;

public class Board {
    private long id;
    private String name;
    private List<BoardColumn> boardColumnList;

    public Board(String name, List<BoardColumn> boardColumnList) {
        this.name = name;
        this.boardColumnList = boardColumnList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BoardColumn> getBoardColumnList() {
        return boardColumnList;
    }

    public void setBoardColumnList(List<BoardColumn> boardColumnList) {
        this.boardColumnList = boardColumnList;
    }
}
