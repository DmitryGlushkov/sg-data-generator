package com.rs.sg.datagen.model;

public class Column {

    private final String name;
    private final String udtName;
    private final boolean isNullable;
    private final DataType dataType;
    private final int maxLength;
    private Definition definition;

    public Column(String name, boolean isNullable, DataType dataType, String udtName, int maxLength){
        this.name = name;
        this.udtName = udtName;
        this.isNullable = isNullable;
        this.dataType = dataType;
        this.maxLength = maxLength;
        if (dataType == DataType.BUILT_IN) {
            definition = PostgresUdt.getDefinition(udtName);
        } else {
            definition = Definition.SEQUENCE;
        }
    }

    public String getName() {
        return name;
    }

    public String getUdtName() {
        return udtName;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public DataType getDataType() {
        return dataType;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }
}
