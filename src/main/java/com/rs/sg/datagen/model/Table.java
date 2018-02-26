package com.rs.sg.datagen.model;

import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
public class Table {

    private Map<String, Column> columnsMap = new HashMap<>();

    private String name = "";

    private List<Column> columns;

    private int rowCount;

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
        columnsMap.clear();
        for (Column c : columns) {
            columnsMap.put(c.getName(), c);
        }
    }

    public boolean hasColumns() {
        return columns != null && columns.size() > 0;
    }

    public Column getColumnByName(String colName) {
        return columnsMap.get(colName);
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
