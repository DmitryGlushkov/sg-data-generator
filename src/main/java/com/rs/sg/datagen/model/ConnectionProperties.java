package com.rs.sg.datagen.model;

import org.springframework.context.annotation.Bean;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ConnectionProperties {

    private String dbHost = "localhost";

    private Integer dbPort = 5432;

    private String dbName = "servergraph";

    private String login = "sg";

    private String pass = "sg-pass";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
