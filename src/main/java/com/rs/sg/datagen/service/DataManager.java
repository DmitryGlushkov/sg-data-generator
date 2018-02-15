package com.rs.sg.datagen.service;

import com.rs.sg.datagen.db.Connector;
import com.rs.sg.datagen.model.ConnectionProperties;
import org.springframework.stereotype.Service;

@Service
public class DataManager {

    private Connector connector;

    public void createConnection(ConnectionProperties properties) throws Exception{
        if (connector != null && connector.isActive()) {
            connector.dismiss();
        }
        connector = new Connector(
                properties.getDbHost(),
                properties.getDbPort(),
                properties.getDbName(),
                properties.getLogin(),
                properties.getPass());
    }

}
