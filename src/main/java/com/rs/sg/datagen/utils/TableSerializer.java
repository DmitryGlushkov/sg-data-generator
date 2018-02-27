package com.rs.sg.datagen.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rs.sg.datagen.model.Column;
import com.rs.sg.datagen.model.Table;

import java.lang.reflect.Type;

import static com.rs.sg.datagen.utils.Constants.COUNT_POSTFIX;

public class TableSerializer implements JsonSerializer<Table> {

    @Override
    public JsonElement serialize(Table table, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        for (final Column col : table.getColumns()) {
            jsonObject.addProperty(col.getName(), col.definitionFormatted(true));
        }
        jsonObject.addProperty(String.format("%s%s", table.getName(), COUNT_POSTFIX), table.getColumns().size());
        return jsonObject;
    }

}
