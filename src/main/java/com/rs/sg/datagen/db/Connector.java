package com.rs.sg.datagen.db;

import com.rs.sg.datagen.model.Column;
import com.rs.sg.datagen.model.Level;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public List<String> queryTableNames() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_type='BASE TABLE'");
        List<String> names = new LinkedList<>();
        while (resultSet.next()) {
            names.add(resultSet.getString(1).toLowerCase());
        }
        statement.close();
        return names;
    }

    public List<Column> queryTableColumns(String tName) throws Exception {
        Statement statement = connection.createStatement();
        String sql = String.format("SELECT column_name, is_nullable, data_type, udt_name, character_maximum_length FROM information_schema.columns where table_name = '%s'", tName);
        ResultSet resultSet = statement.executeQuery(sql);
        List<Column> columns = new ArrayList<>();
        while (resultSet.next()){
            // column_name
            String colName = resultSet.getString(1);
            // is_nullable
            boolean isNullable = resultSet.getBoolean(2);
            // data_type
            Level level = null;
            switch (resultSet.getString(3)) {
                case "ARRAY":
                    level = Level.ARRAY;
                    break;
                case "USER-DEFINED":
                    level = Level.USER_DEFINED;
                    break;
                default:
                    level = Level.BUILT_IN;
                    break;
            }
            // udt_name
            String udtName = resultSet.getString(4);
            // character_maximum_length
            int len = resultSet.getInt(5);

            Column column = new Column(colName, isNullable, level, udtName, len);
            if (level.equals(Level.USER_DEFINED)) {

            }


            columns.add(column);
        }
        statement.close();
        return columns;
    }


}
