package com.rs.sg.datagen.utils;

import com.rs.sg.datagen.model.Column;
import com.rs.sg.datagen.model.ConnectionProperties;
import com.rs.sg.datagen.model.Table;

import static com.rs.sg.datagen.utils.Constants.CFG_PREFIX;
import static com.rs.sg.datagen.utils.Constants.COUNT_POSTFIX;
import static com.rs.sg.datagen.utils.Constants.DB_DSN;

public class ConfigPrinter {

    public static String print(ConnectionProperties con, Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s = \"%s:%s:%s:%s\"\n\n", DB_DSN, con.getDbHost(), con.getDbName(), con.getLogin(), con.getPass()));
        builder.append(String.format("%s%s = {\n", CFG_PREFIX, table.getName()));
        for (Column col : table.getColumns()) {
            builder.append(String.format("    '%s': '%s',\n", col.getName(), col.definitionFormatted(true)));
        }
        builder.append(String.format("    '%s%s': '%d',\n", table.getName(), COUNT_POSTFIX, table.getRowCount()));
        builder.append(String.format("}"));
        return builder.toString();
    }

}
