package com.example.demofx.Utils.Aggregators;

import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.HashSet;

public class LevelItemButtonsAggregator {

    private static LevelItemButtonsAggregator levelItemAggregator;
    private HashMap<String, EventHandler<javafx.event.ActionEvent>> levelItems;
    private HashMap<String, String> ItemIcons;


    public static synchronized LevelItemButtonsAggregator getInstance() {
        if (levelItemAggregator == null) {
            levelItemAggregator = new LevelItemButtonsAggregator();
            levelItemAggregator.levelItems = new HashMap<String, EventHandler<javafx.event.ActionEvent>>();
            levelItemAggregator.ItemIcons = new HashMap<String, String>();
        }
        return levelItemAggregator;
    }

    public void registerNewButtonItem(String name, EventHandler<javafx.event.ActionEvent> event, String pathToImage){
        levelItems.put(name, event);
        ItemIcons.put(name, pathToImage);
    }


    public HashMap<String, EventHandler<javafx.event.ActionEvent>> getItems() {
        return levelItems;
    }

    public String getPathToIcon(String key)
    {
        return ItemIcons.get(key);
    }



    public HashMap<String, String> getItemIcons() {
        return ItemIcons;
    }

    public void setItemIcons(HashMap<String, String> itemIcons) {
        ItemIcons = itemIcons;
    }

}
