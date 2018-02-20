package com.rs.sg.datagen.model;

public enum Definition {

    // 1 - for built-in types
    STRING,
    INTEGER,
    DOUBLE,
    DATE,

    // 2 - for customs
    LINK,
    SEQUENCE,
    INDEX,
    QUERY
    ;

    @Override
    public String toString() {
        return super.toString();
    }
}
