package com.example.demofx.Utils.Serializers.ProjectModel.ToJson;

import com.example.demofx.Models.Basic.WayPoint;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class WayPointSerializer implements JsonSerializer<WayPoint> {
    @Override
    public JsonElement serialize(WayPoint src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("id", src.getItemId());
        result.addProperty("name", src.getName());
        result.addProperty("x", src.getX());
        result.addProperty("y", src.getY());
        result.addProperty("r", src.getRadius());
        result.addProperty("out", src.isOutdoorConnected());

        result.addProperty("lvl", src.getFromLevelId());

        if(src.getFinishWayPoint() == null)
            result.addProperty("fnsh", -1);
        else
            result.addProperty("fnsh", src.getFinishWayPoint().GetId());

        return result;
    }
}
