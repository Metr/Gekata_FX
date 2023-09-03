package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.Building;
import com.example.demofx.Models.Basic.InterestPoint;
import com.example.demofx.Models.Basic.Level;
import com.google.gson.*;

import java.lang.reflect.Type;

public class BuildingDeserializer implements JsonDeserializer<Building> {

    @Override
    public Building deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Building building = new Building();

        JsonObject jsonObject = json.getAsJsonObject();


        building.setName(jsonObject.get("name").getAsString());
        building.setAdress(jsonObject.get("adr").getAsString());
        building.setGoogle_adress(jsonObject.get("g_a").getAsString());
        building.setYandex_adress(jsonObject.get("y_a").getAsString());
        building.setOsm_adress(jsonObject.get("o_a").getAsString());
        building.setTwoGis_adress(jsonObject.get("2_a").getAsString());

        JsonArray levels = jsonObject.getAsJsonArray("lvls");
        for (JsonElement level : levels) {
            building.getLevels().add((Level) context.deserialize(level, Level.class));
        }

        building.InitGraphData();
        return building;
    }
}
