package com.rs.sg.datagen.model;

import com.rs.sg.datagen.service.DataManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class GuiModel {

    @ManagedProperty("#{dataManager}")
    private DataManager dataManager;

    @ManagedProperty("#{connectionProperties}")
    private ConnectionProperties connectionProperties;

    public void onConnect() {
        System.out.println("GuiModel: onConnect1" + dataManager);
        System.out.println("GuiModel: onConnect2" + connectionProperties);
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
}
