package com.rs.sg.datagen.service;

import com.rs.sg.datagen.db.Connector;
import com.rs.sg.datagen.model.ConnectionProperties;
import com.rs.sg.datagen.model.TableSchema;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DataManager {

    private Connector connector;

    public void createConnection(ConnectionProperties properties) throws Exception {
        if (hasConnector()) {
            connector.dismiss();
        }
        connector = new Connector(
                properties.getDbHost(),
                properties.getDbPort(),
                properties.getDbName(),
                properties.getLogin(),
                properties.getPass());
    }


    public List<String> getTableNames() throws Exception {
        if (hasConnector()) {
            return connector.queryTableNames();
        } else {
            return Collections.emptyList();
        }
    }

    private boolean hasConnector() throws Exception {
        return connector != null && connector.isActive();
    }

    public TableSchema getTableSchema(String tName) throws Exception {
        if (hasConnector()) {
            return connector.queryTableSchema(tName);
        }
        return null;
    }

}
