package com.rs.sg.datagen.model;

import com.rs.sg.datagen.service.DataManager;
import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

@ManagedBean
@ViewScoped
public class GuiModel {

    private boolean isConnected = false;
    private List<String> tables = new ArrayList<>();
    private String selectedTable = "";

    @ManagedProperty("#{dataManager}")
    private DataManager dataManager;

    @ManagedProperty("#{connectionProperties}")
    private ConnectionProperties connectionProperties;

    public void onConnect() {
        tables.clear();
        try {
            dataManager.createConnection(connectionProperties);
            tables.addAll(dataManager.getTableNames());
            isConnected = true;
        } catch (Exception e) {
            isConnected = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO, "Connected", connectionProperties.toString()));

    }

    public List<String> completeTable(String query) {
        final String queryLower = query.toLowerCase();
        return tables.stream().filter(t -> t.contains(queryLower)).collect(Collectors.toList());
    }

    public void requestSchema(AjaxBehaviorEvent event) {
        System.out.println("requestSchema!");
        try {
            TableSchema schema = dataManager.getTableSchema(selectedTable);
            System.out.println(1);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
        }
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setConnectionProperties(ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public ConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(String selectedTable) {
        this.selectedTable = selectedTable;
    }
}
