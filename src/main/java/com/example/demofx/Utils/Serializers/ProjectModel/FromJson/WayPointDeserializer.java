package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.InterestPoint;
import com.example.demofx.Models.Basic.WayPoint;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.google.gson.*;

import java.lang.reflect.Type;

public class WayPointDeserializer implements JsonDeserializer<WayPoint> {

    @Override
    public WayPoint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WayPoint point = new WayPoint();

        JsonObject jsonObject = json.getAsJsonObject();

        point.setItemId(jsonObject.get("id").getAsInt());
        point.setName(jsonObject.get("name").getAsString());

        point.setX_pos(jsonObject.get("x").getAsDouble());
        point.setY_pos(jsonObject.get("y").getAsDouble());
        point.setRadius(jsonObject.get("r").getAsDouble());
        point.setOutdoorConnected(jsonObject.get("r").getAsBoolean());

        point.setFromLevelId(jsonObject.get("lvl").getAsInt());
        point.setFinishWaypointIdBuffer(jsonObject.get("fnsh").getAsInt());
        point.InitGraphData();

        return point;
    }
}
