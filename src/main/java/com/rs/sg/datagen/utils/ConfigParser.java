package com.rs.sg.datagen.utils;

import com.rs.sg.datagen.model.Column;
import com.rs.sg.datagen.model.ConnectionProperties;
import com.rs.sg.datagen.model.Definition;
import com.rs.sg.datagen.model.Table;
import com.rs.sg.datagen.service.DataManager;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.rs.sg.datagen.utils.Constants.*;

public class ConfigParser {

    //private static final String PATTERN = "\\s*'([a-zA-Z_]+)':\\s+'([a-z\\s\\.\\d]+)'.+";
    //private static final Pattern ptrn = Pattern.compile(PATTERN);
    private static final SimpleDateFormat sdf = new SimpleDateFormat(SDF_FORMAT);

    public static List<Table> parse(final String[] lines, DataManager dataManager) throws Exception {

        List<Table> result = new ArrayList<>();

        Table table = null;

        for (String line : lines) {
            if (line.isEmpty()) continue;
            line = line.trim();
            if (line.startsWith(DB_DSN)) {
                String dns = line.split("=")[1].trim();
                String[] dnsSplit = dns.split(":");
                ConnectionProperties connect = new ConnectionProperties();
                connect.setDbHost(dnsSplit[0]);
                connect.setDbName(dnsSplit[1]);
                connect.setLogin(dnsSplit[2]);
                connect.setPass(dnsSplit[3]);
            } else if (line.startsWith(CFG_PREFIX)) {
                String tableName = line.substring(4,line.indexOf(" "));
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

                // rows count option
                if (colName.equals(String.format("%s%s", table.getName(), COUNT_POSTFIX))) {
                    table.setRowCount(Integer.parseInt(options));
                }
                // column option
                else {
                    Definition definition = Definition.valueOf(options.split(" ")[0].trim().toUpperCase());
                    Column column = table.getColumnByName(colName);
                    column.setDefinition(definition);
                    if (definition == Definition.SEQUENCE) {
                        List<String> seq = Arrays.asList(options.substring(options.indexOf(" "), options.length()).replace("[", "").replace("]", "").replace(" ", "").replace("\"", "").split(","));
                        column.setConstraint2(seq, "entries");
                    } else if (definition == Definition.STRING) {
                        String[] vals = options.substring(options.indexOf(' ') + 1).split("\\.\\.");
                        column.setConstraint2(vals[0], "minLen");
                        column.setConstraint2(vals[1], "maxLen");
                    } else if (definition == Definition.INTEGER) {
                        String[] vals = options.substring(options.indexOf(' ') + 1).split("\\.\\.");
                        column.setConstraint2(vals[0], "startNumber");
                        column.setConstraint2(vals[1], "endNumber");
                    } else if (definition == Definition.DATE) {
                        String[] vals = options.substring(options.indexOf(' ') + 1).split("\\.\\.");
                        column.setConstraint2(sdf.parse(vals[0]), "start");
                        column.setConstraint2(sdf.parse(vals[1]), "end");
                    } else if (definition == Definition.LINK) {
                        String[] vals = options.substring(options.indexOf(' ') + 1).split("\\.\\.");
                        column.setConstraint2(vals[0], "table");
                        column.setConstraint2(vals[1], "column");
                    }
                }
            }
        }
        return result;
    }
}
