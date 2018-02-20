package com.rs.sg.datagen.model;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class Table {

    private String name = "";

    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public boolean hasColumns() {
        return columns != null && columns.size() > 0;
    }

}
