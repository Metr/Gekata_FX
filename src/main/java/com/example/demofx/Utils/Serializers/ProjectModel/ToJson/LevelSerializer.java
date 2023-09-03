package com.example.demofx.Utils.Serializers.ProjectModel.ToJson;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Models.Basic.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LevelSerializer implements JsonSerializer<Level> {

    @Override
    public JsonElement serialize(Level src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("id", src.GetId());
        result.addProperty("name", src.getName());
        result.addProperty("img", src.getImagePath());

        JsonArray WPoints = new JsonArray();
        result.add("w_p", WPoints);
        for (IGraphPrimitive point : src.getWayPoints()) {
            WPoints.add(context.serialize((WayPoint)point));
        }

        JsonArray IPoints = new JsonArray();
        result.add("i_p", IPoints);
        for (IGraphPrimitive point : src.getInterestPoints()) {
            IPoints.add(context.serialize((InterestPoint)point));
        }

        JsonArray walls = new JsonArray();
        result.add("walls", walls);
        for (IGraphPrimitive wall : src.getWalls()) {
            walls.add(context.serialize((BasicWall)wall));
        }

        JsonArray curveWalls = new JsonArray();
        result.add("curve", curveWalls);
        for (IGraphPrimitive wall : src.getCurveWalls()) {
            curveWalls.add(context.serialize((QuadCurveWall)wall));
        }


        return result;
    }
}
