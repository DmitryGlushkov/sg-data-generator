package com.rs.sg.datagen.model;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

    public String definitionFormatted(boolean detailed){
        return detailed ?
                String.format("%s %s", definition.toString().toLowerCase(), constraint) :
                String.format("%s %s", definition.toString().toLowerCase(), constraint.minify());
    }

    public void setDefinition(Definition def) throws Exception {
        if (!definition.equals(def)) {
            this.definition = def;
            constraint = Constraint.createConstraint(definition);
        }
    }

    public Object constraint(Object o) {
        try {
            String varName = (String) o;
            return constraint.getClass().getDeclaredField(varName).get(constraint);
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
        }
        return null;
    }

    public void setConstraint(Object o) {
        try {
            String varName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("varName");
            if(constraint.getClass().getDeclaredField(varName).getAnnotatedType().getType() == int.class){
                o = Integer.valueOf((String) o);
            }
            constraint.getClass().getDeclaredField(varName).set(constraint, o);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
        }
    }

}
