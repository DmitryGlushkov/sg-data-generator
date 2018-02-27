package com.rs.sg.datagen.utils;

import com.google.gson.*;
import com.rs.sg.datagen.model.TableOptions;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TableDeserializer implements JsonDeserializer<TableOptions> {

    @Override
    public TableOptions deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        TableOptions options = new TableOptions();
        Set<Map.Entry<String, JsonElement>> jSet = ((JsonObject) jsonElement).entrySet();
        Iterator<Map.Entry<String, JsonElement>> it = jSet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, JsonElement> e = it.next();
            String colName = e.getKey();
            String opt = e.getValue().getAsString();
            options.getOptionsMap().put(colName, opt);
        }
        return options;
    }
}
