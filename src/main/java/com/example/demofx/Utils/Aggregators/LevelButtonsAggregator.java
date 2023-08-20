package com.example.demofx.Utils.Aggregators;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashMap;

public class LevelButtonsAggregator {

    private static LevelButtonsAggregator levelItemAggregator;
    private HashMap<String, EventHandler<ActionEvent>> levelItems;
    private HashMap<String, String> ItemIcons;


    public static synchronized LevelButtonsAggregator getInstance() {
        if (levelItemAggregator == null) {
            levelItemAggregator = new LevelButtonsAggregator();
            levelItemAggregator.levelItems = new HashMap<String, EventHandler<javafx.event.ActionEvent>>();
        }
        return levelItemAggregator;
    }

    public void registerNewButtonItem(String name, EventHandler<javafx.event.ActionEvent> event){
        levelItems.put(name, event);
    }


    public void changeKey(String oldValue, String newValue){
        if(levelItems.containsKey(oldValue)){
            levelItems.put(newValue, levelItems.get(oldValue));
        }
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
