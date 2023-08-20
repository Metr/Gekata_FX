package com.example.demofx.Models.Basic;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class HouseProject {

    private static HouseProject houseProject;
    private Building building;

    private String ProjectName;

    private NodeModelContainer SelectedItem;

    private boolean isNoErrors;

    public static synchronized HouseProject getInstance() {
        if (houseProject == null) {
            houseProject = new HouseProject();
            houseProject.building = new Building();
            houseProject.isNoErrors = true;
            houseProject.ProjectName = "Default";
        }
        return houseProject;
    }



    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public NodeModelContainer getSelectedItem() {
        return SelectedItem;
    }

    public void setSelectedItem(NodeModelContainer selectedItem) {
        SelectedItem = selectedItem;
    }

    ///PROJECT FILE////////////////////////////////////////

    public SortedMap<String, String> CheckErrors(){
        SortedMap<String,String> report = new TreeMap<>();
        report = this.building.ModelErrorsCheck(report);

        for (String key : report.keySet()) {
            String value = report.get(key);
            System.out.println(key + ": " + value);
            if(key.substring(0, 1).equals("0"))
                this.isNoErrors = false;
        }

        return report;
    }

    public void saveProject(){
//        Gson gson = new GsonBuilder()
//                //.setPrettyPrinting()
//                .create();
//        String json = gson.toJson(band);
    }

}


