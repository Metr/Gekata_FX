package com.example.demofx.Utils.Fabrics;

public class ItemIdFabric {

    private static int counter;

    public static int getCounter(){
        return counter++;
    }

    public static void resetCounter(){
        counter = 0;
    }
}
