package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.Building;
import com.example.demofx.Models.Basic.HouseProject;
import com.example.demofx.Models.Basic.Level;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ProjectDeserializer implements JsonDeserializer<HouseProject> {

    @Override
    public HouseProject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        HouseProject.getInstance().setProjectName(jsonObject.get("pname").getAsString());
        HouseProject.getInstance().setProjectPath(jsonObject.get("ppath").getAsString());

        JsonArray building = jsonObject.getAsJsonArray("bldg");
        for (JsonElement model : building) {
            HouseProject.getInstance().setBuilding((Building) context.deserialize(model, Building.class));
        }

        HouseProject.getInstance().getBuilding().getRestoredWaypointsConnectList();

        return null;
    }
}
