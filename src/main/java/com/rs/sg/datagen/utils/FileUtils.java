package com.rs.sg.datagen.utils;

import com.rs.sg.datagen.model.*;
import com.rs.sg.datagen.service.DataManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rs.sg.datagen.utils.Constants.*;
import static com.rs.sg.datagen.utils.FileUtils.Print.PY;

public class FileUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat(SDF_FORMAT);

    public enum Print {
        PY, JSON
    }

    public static List<Table> parse(final String[] lines, DataManager dataManager, Print p) throws Exception {
        if (PY == p) {
            return parsePy(lines, dataManager);
        } else {
            return parseJson(lines, dataManager);
        }
    }

    public static String print(ConnectionProperties con, Table table, DataManager dataManager, Print p) {
        if (PY == p) {
            return printPy(con, table);
        } else {
            return printJson(con, table, dataManager);
        }
    }

    public static List<Table> parseJson(final String[] lines, DataManager dataManager) throws Exception {
        List<Table> tables = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) continue;
            line = line.trim();
            if (line.startsWith(DB_DSN)) {
                ConnectionProperties connectionProperties = parseConnections(line);
            } else if (line.startsWith(CFG_PREFIX)) {
                String[] split = line.split("=");
                String tableName = split[0].trim().replace(CFG_PREFIX, "");
                TableOptions options = dataManager.getGson().fromJson(split[1].trim(), TableOptions.class);
                Table table = table = new Table();
                table.setName(tableName);
                table.setColumns(dataManager.getColumns(tableName));
                tables.add(table);
                for (String colName : options.getOptionsMap().keySet()) {
                    parseColumnOptions(table, colName, options.getOptionsMap().get(colName));
                }
            }
        }
        return tables;
    }

    public static String printJson(ConnectionProperties con, Table table, DataManager dataManager) {
        String db = getConnectionString(con);
        String json = String.format("%s%s = %s",
                CFG_PREFIX,
                table.getName(),
                dataManager.getGson().toJson(table));
        return String.format("%s\n%s", db, json);
    }

    public static List<Table> parsePy(final String[] lines, DataManager dataManager) throws Exception {

        List<Table> result = new ArrayList<>();

        Table table = null;

        for (String line : lines) {
            if (line.isEmpty()) continue;
            line = line.trim();
            if (line.startsWith(DB_DSN)) {
                ConnectionProperties connectionProperties = parseConnections(line);
            } else if (line.startsWith(CFG_PREFIX)) {
                String tableName = line.substring(4, line.indexOf(" "));
                if (dataManager.getTableNames().contains(tableName)) {
                    table = new Table();
                    table.setName(tableName);
                    table.setColumns(dataManager.getColumns(tableName));
                    result.add(table);
                }
            } else if (line.equals("}")) {
                table = null;
            } else if (table != null) {
                int ind1 = 1;
                int ind2 = line.indexOf("'", ind1);
                int ind3 = line.indexOf("'", ind2 + 1) + 1;
                int ind4 = line.indexOf("'", ind3 + 1);

                String colName = line.substring(ind1, ind2);
                String options = line.substring(ind3, ind4);

                parseColumnOptions(table, colName, options);
            }
        }
        return result;
    }

    public static void parseColumnOptions(Table table, String colName, String options) throws Exception {
        // rows count option
        if (colName.equals(String.format("%s%s", table.getName(), COUNT_POSTFIX))) {
            table.setRowCount(Integer.parseInt(options));
        }
        // column option
        else {
            String defStr = options.substring(0, options.indexOf(' ')).trim();
            String varStr = options.substring(options.indexOf(' ') + 1).trim();
            Definition definition = Definition.valueOf(defStr.toUpperCase());
            Column column = table.getColumnByName(colName);
            column.setDefinition(definition);
            switch (definition.varNames().length) {
                case 2:
                    String[] vars;
                    if(definition == Definition.LINK){
                        vars = varStr.split("\\.");
                    } else {
                        vars = varStr.split("\\.\\.");
                    }
                    column.setConstraint2(vars[0], definition.varNames()[0]);
                    column.setConstraint2(vars[1], definition.varNames()[1]);
                    break;
                case 1:
                    Object o;
                    if (definition == Definition.SEQUENCE) {
                        o = Arrays.asList(varStr.replace("[", "").replace("]", "").replace(" ", "").replace("\"", "").split(","));
                    } else {
                        o = varStr;
                    }
                    column.setConstraint2(o, definition.varNames()[0]);
                    break;
            }
        }
    }

    private static ConnectionProperties parseConnections(String line) {
        String dns = line.split("=")[1].trim();
        String[] dnsSplit = dns.split(":");
        ConnectionProperties connect = new ConnectionProperties();
        connect.setDbHost(dnsSplit[0]);
        connect.setDbName(dnsSplit[1]);
        connect.setLogin(dnsSplit[2]);
        connect.setPass(dnsSplit[3]);
        return connect;
    }

    public static String printPy(ConnectionProperties con, Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append(getConnectionString(con));
        builder.append(String.format("%s%s = {\n", CFG_PREFIX, table.getName()));
        for (Column col : table.getColumns()) {
            builder.append(String.format("    '%s': '%s',\n", col.getName(), col.definitionFormatted(true)));
        }
        builder.append(String.format("    '%s%s': '%d',\n", table.getName(), COUNT_POSTFIX, table.getRowCount()));
        builder.append(String.format("}"));
        return builder.toString();
    }

    private static String getConnectionString(ConnectionProperties con) {
        return String.format("%s = \"%s:%s:%s:%s\"\n", DB_DSN, con.getDbHost(), con.getDbName(), con.getLogin(), con.getPass());
    }
}
