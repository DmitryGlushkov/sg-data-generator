package com.rs.sg.datagen.model;

import javax.faces.event.AjaxBehaviorEvent;

public class Column {

    private final String name;
    private final String dataType;
    private final boolean isNullable;
    private final Level level;
    private final int maxLength;
    private Definition definition;

    public Column(String name, boolean isNullable, Level level, String dataType, int maxLength){
        this.name = name;
        this.dataType = dataType;
        this.isNullable = isNullable;
        this.level = level;
        this.maxLength = maxLength;
        if (level == Level.BUILT_IN) {
            definition = PostgresUdt.getDefinition(dataType);
        } else {
            definition = Definition.SEQUENCE;
        }
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public Level getLevel() {
        return level;
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

    public void onIntChange(AjaxBehaviorEvent event){
        System.out.println("onIntChange");
    }
}
