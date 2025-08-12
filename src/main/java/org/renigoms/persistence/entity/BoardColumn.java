package org.renigoms.persistence.entity;

public class BoardColumn {

    private long id;
    private String name;
    private String kind;
    private int order;
    private Board board;

    public BoardColumn(String name, String kind, int order, Board board) {
        this.name = name;
        this.kind = kind;
        this.order = order;
        this.board = board;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
