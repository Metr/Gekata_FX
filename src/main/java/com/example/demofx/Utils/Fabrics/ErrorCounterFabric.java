package com.example.demofx.Utils.Fabrics;

public class ErrorCounterFabric {

    private static int counter;

    public static int getCounter(){
        return counter++;
    }


    public static void resetCounter(){
        counter = 0;
    }

}
