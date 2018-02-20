package com.rs.sg.datagen.model;

import java.util.HashMap;
import java.util.Map;

public class PostgresUdt {

    // map for built-in types
    private static Map<String, Definition> udtMap = new HashMap<>();

    static {

        // DATE
        udtMap.put("abstime", Definition.DATE);
        udtMap.put("date", Definition.DATE);
        udtMap.put("datetime", Definition.DATE);
        udtMap.put("interval", Definition.DATE);
        udtMap.put("reltime", Definition.DATE);
        udtMap.put("time", Definition.DATE);
        udtMap.put("timestamp", Definition.DATE);
        udtMap.put("timespan", Definition.DATE);
        udtMap.put("timestamptz", Definition.DATE);

        // INTEGER
        udtMap.put("int2", Definition.INTEGER);
        udtMap.put("int4", Definition.INTEGER);
        udtMap.put("int8", Definition.INTEGER);
        udtMap.put("bool", Definition.INTEGER);
        udtMap.put("numeric", Definition.INTEGER);

        // DOUBLE
        udtMap.put("float4", Definition.DOUBLE);
        udtMap.put("float8", Definition.DOUBLE);

        // STRING
        udtMap.put("char", Definition.STRING);
        udtMap.put("char2", Definition.STRING);
        udtMap.put("char4", Definition.STRING);
        udtMap.put("char8", Definition.STRING);
        udtMap.put("char16", Definition.STRING);
        udtMap.put("varchar", Definition.STRING);
        udtMap.put("text", Definition.STRING);
        udtMap.put("name", Definition.STRING);      // 64 bytes
        udtMap.put("\"char\"", Definition.STRING);  // 1 bytes

        // BYTE
        //udtMap.put("bytea", Definition.BYTE);      // variable-length binary string
        udtMap.put("bytea", Definition.INTEGER);      // variable-length binary string

        // NETWORK
        udtMap.put("inet", Definition.STRING);      // IPv4 and IPv6 networks: 7 or 19 bytes
        udtMap.put("cidr", Definition.STRING);      // IPv4 and IPv6 hosts and networks: 7 or 19 bytes
        udtMap.put("macaddr", Definition.STRING);   // 6 bytes

        // ---> not supported:

        // Object Identifier Types
        // Pseudo-Types
        // UUID Type
        // XML Type
        // Geometric Types

        // udtMap.put("anyarray", Definition.STRING);
        // udtMap.put("bpchar", Definition.STRING);
        // udtMap.put("oid", Definition.STRING);
        // udtMap.put("pg_dependencies", Definition.STRING);
        // udtMap.put("pg_lsn", Definition.STRING);
        // udtMap.put("pg_ndistinct", Definition.STRING);
        // udtMap.put("pg_node_tree", Definition.STRING);
        // udtMap.put("regproc", Definition.STRING);
        // udtMap.put("regtype", Definition.STRING);
        // udtMap.put("xid", Definition.STRING);

    }

    public static Definition getDefinition(String udtBultIn) {
        return udtMap.get(udtBultIn);
    }
}
