package com.example.demofx.Models;

import com.example.demofx.Utils.Containers.NodeModelContainer;

import javax.swing.tree.TreeNode;

public class HouseProject {

    private static HouseProject houseProject;
    private Building building;

    private NodeModelContainer SelectedItem;


    public static synchronized HouseProject getInstance() {
        if (houseProject == null) {
            houseProject = new HouseProject();
            houseProject.building = new Building();
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
}


