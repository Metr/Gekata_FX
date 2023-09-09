package com.example.demofx.Modules.BottomOutput;

import com.example.demofx.Models.Basic.HouseProject;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.SortedMap;


public class BottomOutputProvider {

    private TextArea outputArea;

    private PropertyChangeSupport support;

    public BottomOutputProvider(TextArea area) {
        this.outputArea = area;
        support = new PropertyChangeSupport(this);
    }

    public void setText(SortedMap<String, String> report){
        StringBuilder str = new StringBuilder("Errors:\n");
        boolean flag = true;

        for(Map.Entry<String, String> item : report.entrySet()){
            if(item.getKey().compareTo("10001") >= 0 && flag){
                str.append("\nWarnings:\n");
                flag = false;
            }
            str.append(item.getKey() + ": " + item.getValue() + "\n");
        }



        this.outputArea.setText(str.toString());
    }



}
