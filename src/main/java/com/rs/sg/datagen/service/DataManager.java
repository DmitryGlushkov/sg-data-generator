package com.rs.sg.datagen.service;

import com.google.gson.*;
import com.rs.sg.datagen.db.Connector;
import com.rs.sg.datagen.model.Column;
import com.rs.sg.datagen.model.ConnectionProperties;
import com.rs.sg.datagen.model.Table;
import com.rs.sg.datagen.model.TableOptions;
import com.rs.sg.datagen.utils.TableDeserializer;
import com.rs.sg.datagen.utils.TableSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DataManager {

    private Connector connector;

    private Gson gson;

    @PostConstruct
    void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Table.class, new TableSerializer());
        gsonBuilder.registerTypeAdapter(TableOptions.class, new TableDeserializer());
        gson = gsonBuilder.create();
    }

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

    public List<Column> getColumns(String tName) throws Exception {
        if (hasConnector()) {
            return connector.queryTableColumns(tName);
        }
        return null;
    }

    public Gson getGson() {
        return gson;
    }

}
