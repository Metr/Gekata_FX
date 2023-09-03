package com.example.demofx.Utils.Serializers.ProjectModel.ToJson;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Models.Basic.HouseProject;
import com.example.demofx.Models.Basic.Level;
import com.example.demofx.Models.Basic.WayPoint;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ProjectSerializer implements JsonSerializer<HouseProject> {

    @Override
    public JsonElement serialize(HouseProject src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("pname", src.getProjectName());
        result.addProperty("ppath", src.getProjectPath());

        JsonArray building = new JsonArray();
        result.add("bldg", building);
        building.add(context.serialize(src.getBuilding()));


        return result;

    }
}
