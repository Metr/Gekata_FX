package com.example.demofx.Utils.Enums;

import java.util.HashMap;

public class EnumUtils {

    public static HashMap<String, String> TypesToMap(){
        HashMap<String,String> map = new HashMap<>();

        map.put(DescriptionTypes.TEXT_DESCR.name(), DescriptionTypes.TEXT_DESCR.name());
        map.put(DescriptionTypes.GRID_DESCR.name(), DescriptionTypes.GRID_DESCR.name());
        map.put(DescriptionTypes.CHAT_DESCR.name(), DescriptionTypes.CHAT_DESCR.name());
        return map;
    }








}
