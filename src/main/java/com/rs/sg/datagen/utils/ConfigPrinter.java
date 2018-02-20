package com.rs.sg.datagen.utils;

import com.rs.sg.datagen.model.Column;
import com.rs.sg.datagen.model.ConnectionProperties;
import com.rs.sg.datagen.model.Table;

public class ConfigPrinter {

    public static String print(ConnectionProperties con, Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("db_dsn = \"%s:%s:%s:%s\"\n\n", con.getDbHost(), con.getDbName(), con.getLogin(), con.getPass()));
        builder.append(String.format("CFG_%s = {\n", table.getName()));
        for (Column col : table.getColumns()) {
            builder.append(String.format("    '%s': '%s',\n", col.getName(), col.definitionFormatted(true)));
        }
        builder.append(String.format("}"));
        return builder.toString();
    }

}
