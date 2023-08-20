package com.example.demofx.Modules.Workbench;

import com.example.demofx.Models.Basic.HouseProject;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WorkbenchProvider implements PropertyChangeListener {

    private HouseProject houseProject;
    private Pane MainCanvas;
    private PropertyChangeSupport support;

    private String selectedLevelName;


    public WorkbenchProvider(Pane canvas) {
        houseProject = HouseProject.getInstance();
        this.MainCanvas = canvas;
        if(!houseProject.getBuilding().getLevels().isEmpty())
            this.selectedLevelName = houseProject.getBuilding().getLevels().get(0).getName();

        support = new PropertyChangeSupport(this);
        workbenchInitialize();
    }

    public void DrawLevel(String key) {
        int index = HouseProject.getInstance().getBuilding().getIndexLevelWithName(key);
        selectedLevelName = key;
        if (index >= 0) {
            MainCanvas.getChildren().clear();
            MainCanvas.getChildren().add(HouseProject.getInstance().getBuilding().getLevels().get(index).getLevelNode());
        }
    }

    public void DrawLevelsGraph(){
     if(HouseProject.getInstance().getBuilding().getLevels().size() >= 2){
         MainCanvas.getChildren().clear();
         MainCanvas.getChildren().add(HouseProject.getInstance().getBuilding().GetLevelsGraphNode());
     }
     else {
         //TODO alert window
         System.out.println("levels.count < 2");
     }
    }

    private void DrawWaypointGraph(){
        if(!HouseProject.getInstance().getBuilding().getLevels().isEmpty()){
            MainCanvas.getChildren().clear();
            MainCanvas.getChildren().add(HouseProject.getInstance().getBuilding().GetWaypointsGraphNode());
        }
        else {
            //TODO alert window
            System.out.println("levels.count < 2");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()){
            case "levelIndex":
                DrawLevel(propertyChangeEvent.getNewValue().toString());
                break;
            case "reRender":
                DrawLevel(selectedLevelName);
                break;
            case "drawLevelGraph":
                DrawLevelsGraph();
                break;
            case "drawWayPointsGraph":
                DrawWaypointGraph();
                break;
            default:
                break;
        }
    }

    public void BroadcastReRenderCommand(){
        support.firePropertyChange("reRender", null, HouseProject.getInstance().getSelectedItem());
    }

    public void BroadcastSelectedItem(){
        support.firePropertyChange("newSelectedItemOnWorkbench", null, HouseProject.getInstance().getSelectedItem());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private void workbenchInitialize(){

    }


    public String getSelectedLevelName() {
        return selectedLevelName;
    }

    public void setSelectedLevelName(String selectedLevelName) {
        this.selectedLevelName = selectedLevelName;
    }
}
