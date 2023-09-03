package com.example.demofx.Utils.Serializers.ProjectModel.ToJson;

import com.example.demofx.Models.Basic.Building;
import com.example.demofx.Models.Basic.Level;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.*;

import java.lang.reflect.Type;

public class BuildingSerializer implements JsonSerializer<Building> {
    @Override
    public JsonElement serialize(Building src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("name", src.getName());
        result.addProperty("adr", src.getAdress());
        result.addProperty("g_a", src.getGoogle_adress());
        result.addProperty("y_a", src.getYandex_adress());
        result.addProperty("o_a", src.getOsm_adress());
        result.addProperty("2_a", src.getTwoGis_adress());

//        result.add("lvls", context.serialize(src.getLevels()));

        JsonArray levels = new JsonArray();
        result.add("lvls", levels);
        for (Level level : src.getLevels()) {
            levels.add(context.serialize(level));
        }

        return result;
    }
}

