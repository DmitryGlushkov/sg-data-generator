package com.rs.sg.datagen.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    private static final String DB_PTRN = "jdbc:postgresql://%s:%d/%s";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Connection connection;

    public Connector(String host, int port, String dbName, String login, String pass) throws Exception {
        connection = DriverManager.getConnection(String.format(DB_PTRN, host, port, dbName), login, pass);
    }

    public boolean isActive() throws Exception {
        return this.connection != null && connection.isValid(0);
    }

    public void dismiss() throws Exception {
        connection.close();
    }


}
