package org.renigoms.persistence.entity;

import java.time.OffsetDateTime;

public class Card {
    private long id;
    private BoardColumn boardColumn;
    private String title;
    private String description;
    private OffsetDateTime createdAt;
}
