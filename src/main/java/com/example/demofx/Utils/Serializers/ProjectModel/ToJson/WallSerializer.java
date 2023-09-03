package com.example.demofx.Utils.Serializers.ProjectModel.ToJson;

import com.example.demofx.Models.Basic.BasicWall;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class WallSerializer implements JsonSerializer<BasicWall>
{
    @Override
    public JsonElement serialize(BasicWall src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("id", src.GetId());
        result.addProperty("spx", src.getStartX());
        result.addProperty("spy", src.getStartY());
        result.addProperty("epx", src.getEndX());
        result.addProperty("epy", src.getEndY());

        return result;
    }
}
