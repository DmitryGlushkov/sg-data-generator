package com.rs.sg.datagen.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connector() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/servergraph", "sguser", "sguser");
        System.out.println(1);
    }


}
