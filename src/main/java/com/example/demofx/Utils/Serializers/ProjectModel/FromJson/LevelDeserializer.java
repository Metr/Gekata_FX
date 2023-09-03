package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LevelDeserializer implements JsonDeserializer<Level> {

    @Override
    public Level deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Level level = new Level();

        JsonObject jsonObject = json.getAsJsonObject();

        level.setItemId(jsonObject.get("id").getAsInt());
        level.setName(jsonObject.get("name").getAsString());
        level.setImagePath(jsonObject.get("img").getAsString());

        JsonArray IPoints = jsonObject.getAsJsonArray("i_p");
        for (JsonElement point : IPoints) {
            level.getInterestPoints().add((InterestPoint) context.deserialize(point, InterestPoint.class));
        }

        JsonArray WPoints = jsonObject.getAsJsonArray("w_p");
        for (JsonElement point : WPoints) {
            level.getWayPoints().add((WayPoint) context.deserialize(point, WayPoint.class));
        }

        JsonArray curves = jsonObject.getAsJsonArray("curve");
        for (JsonElement curve : curves) {
            level.getCurveWalls().add((QuadCurveWall) context.deserialize(curve, QuadCurveWall.class));
        }

        JsonArray walls = jsonObject.getAsJsonArray("walls");
        for (JsonElement wall : walls) {
            level.getWalls().add((BasicWall) context.deserialize(wall, BasicWall.class));
        }

        level.InitGraphData();

        return level;
    }
}
