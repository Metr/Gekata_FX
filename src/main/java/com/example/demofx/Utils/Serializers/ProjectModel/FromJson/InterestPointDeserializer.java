package com.example.demofx.Utils.Serializers.ProjectModel.FromJson;

import com.example.demofx.Models.Basic.InterestPoint;
import com.example.demofx.Models.Basic.QuadCurveWall;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.google.gson.*;

import java.lang.reflect.Type;

public class InterestPointDeserializer implements JsonDeserializer<InterestPoint> {

    @Override
    public InterestPoint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        InterestPoint point = new InterestPoint();

        JsonObject jsonObject = json.getAsJsonObject();

        point.setItemId(jsonObject.get("id").getAsInt());
        point.setObjectName(jsonObject.get("name").getAsString());

        point.setX_pos(jsonObject.get("x").getAsDouble());
        point.setY_pos(jsonObject.get("y").getAsDouble());
        point.setRadius(jsonObject.get("r").getAsDouble());
        point.setDescription(jsonObject.get("descr").getAsString());

        String str = jsonObject.get("type").getAsString();
        switch (str){
            case "GRID_DESCR":
                point.setContentType(DescriptionTypes.GRID_DESCR);
                break;
            case "CHAT_DESCR":
                point.setContentType(DescriptionTypes.CHAT_DESCR);
                break;
            default:
                point.setContentType(DescriptionTypes.TEXT_DESCR);
                break;
        }

        point.InitGraphData();
        return point;
    }
}
