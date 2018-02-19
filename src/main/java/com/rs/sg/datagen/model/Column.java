package com.rs.sg.datagen.model;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

public class Column {

    private final String name;
    private final String dataType;
    private final boolean isNullable;
    private final Level level;
    private final int maxLength;
    private Definition definition;
    private Constraint constraint;

    public Column(String name, boolean isNullable, Level level, String dataType, int maxLength) throws Exception {
        this.name = name;
        this.dataType = dataType;
        this.isNullable = isNullable;
        this.level = level;
        this.maxLength = maxLength;
        if (level == Level.BUILT_IN) {
            definition = PostgresUdt.getDefinition(dataType);
            if (definition == null) definition = Definition.STRING;
        } else {
            definition = Definition.SEQUENCE;
        }
        constraint = Constraint.createConstraint(definition);
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

    public String definitionFormatted(){
        return String.format("%s: %s", definition, constraint);
    }

    public void setDefinition(Definition def) throws Exception {
        if (!definition.equals(def)) {
            this.definition = def;
            constraint = Constraint.createConstraint(definition);
        }
    }

    private Object lolca;

    public void processValueChange(ValueChangeEvent event){
        try {
            String varName = event.getComponent().getId();
            switch (definition) {
                case INTEGER:
                    int value = Integer.parseInt((String) event.getNewValue());
                    Constraint.Integer c = (Constraint.Integer) constraint;
                    c.getClass().getDeclaredField(varName).setInt(c, value);
                    break;
                case STRING:
                    break;
                case SEQUENCE:
                    break;

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
        }

        System.out.println("processValueChange: " + event.getNewValue());

    }

    public Object getLolca() {
        return lolca;
    }

    public void setLolca(Object lolca) {
        this.lolca = lolca;
    }
}
