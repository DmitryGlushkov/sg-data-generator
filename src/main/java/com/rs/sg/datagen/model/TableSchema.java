package com.rs.sg.datagen.model;

import java.util.List;

public class TableSchema {

    private final String tableName;
    private final List<Column> columns;

    public TableSchema(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public static class Column {
        public Column(String name, boolean isNullable, DataType dataType, String udtName, int maxLength){
            this.name = name;
            this.udtName = udtName;
            this.isNullable = isNullable;
            this.dataType = dataType;
            this.maxLength = maxLength;
        }
        public final String name;
        public final String udtName;
        public final boolean isNullable;
        public final DataType dataType;
        public final int maxLength;
    }

}
