package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.BasicWall;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


public class WallDeserializer implements JsonDeserializer<BasicWall> {

    @Override
    public BasicWall deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BasicWall wall = new BasicWall();

        JsonObject jsonObject = json.getAsJsonObject();

        wall.setItemId(jsonObject.get("id").getAsInt());
        wall.setStartPoint(jsonObject.get("spx").getAsDouble(), jsonObject.get("spy").getAsDouble());
        wall.setEndPoint(jsonObject.get("epx").getAsDouble(), jsonObject.get("epy").getAsDouble());

        wall.InitGraphData();

        return wall;
    }
}
