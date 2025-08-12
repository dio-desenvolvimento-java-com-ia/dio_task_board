package org.renigoms.persistence.entity;

import java.time.OffsetDateTime;

public class Block {
    private long id;
    private String blockCause;
    private OffsetDateTime blockIn;
    private String unblockCause;
    private OffsetDateTime unblockIn;

}
