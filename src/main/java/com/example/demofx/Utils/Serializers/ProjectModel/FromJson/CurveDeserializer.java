package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.BasicWall;
import com.example.demofx.Models.Basic.QuadCurveWall;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CurveDeserializer implements JsonDeserializer<QuadCurveWall> {

    @Override
    public QuadCurveWall deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        QuadCurveWall wall = new QuadCurveWall();

        JsonObject jsonObject = json.getAsJsonObject();

        wall.setItemId(jsonObject.get("id").getAsInt());
        wall.setStartPoint(jsonObject.get("spx").getAsDouble(), jsonObject.get("spy").getAsDouble());
        wall.setEndPoint(jsonObject.get("epx").getAsDouble(), jsonObject.get("epy").getAsDouble());
        wall.setControlPoint(jsonObject.get("cpx").getAsDouble(), jsonObject.get("cpy").getAsDouble());

        wall.InitGraphData();

        return wall;
    }

}
