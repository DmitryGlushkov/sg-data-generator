package com.rs.sg.datagen.model;

import com.rs.sg.datagen.service.DataManager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;

@ManagedBean
public class GuiModel {

    private boolean isConnected = false;

    @ManagedProperty("#{dataManager}")
    private DataManager dataManager;

    @ManagedProperty("#{connectionProperties}")
    private ConnectionProperties connectionProperties;

    public void onConnect() {
        try {
            dataManager.createConnection(connectionProperties);
            isConnected = true;
        } catch (Exception e) {
            isConnected = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Exception", e.getMessage() != null ? e.getMessage() : e.toString()));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO, "Connected", connectionProperties.toString()));
        System.out.println("onConnect: " + isConnected);
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
}
