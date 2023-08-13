package com.example.demofx.Utils.Configs;

import com.example.demofx.Models.Building;
import com.example.demofx.Models.HouseProject;
import com.example.demofx.Utils.Containers.NodeModelContainer;

public class WorkbenchProperties {

    private static WorkbenchProperties workbenchProperties;

    private boolean isAllRadiusDrawing;

    private boolean isControlDotsConnecting;

    private int ControlDotsConnectingRadius;

    public WorkbenchProperties() {
        ControlDotsConnectingRadius = 3;
        isControlDotsConnecting = false;
        isAllRadiusDrawing = false;
    }

    public static synchronized WorkbenchProperties getInstance() {
        if (workbenchProperties == null) {
            workbenchProperties = new WorkbenchProperties();
        }
        return workbenchProperties;
    }

    public boolean isAllRadiusDrawing() {
        return isAllRadiusDrawing;
    }

    public void setAllRadiusDrawing(boolean allRadiusDrawing) {
        isAllRadiusDrawing = allRadiusDrawing;
    }

    public boolean isControlDotsConnecting() {
        return isControlDotsConnecting;
    }

    public void setControlDotsConnecting(boolean controlDotsConnecting) {
        isControlDotsConnecting = controlDotsConnecting;
    }

    public int getControlDotsConnectingRadius() {
        return ControlDotsConnectingRadius;
    }

    public void setControlDotsConnectingRadius(int controlDotsConnectingRadius) {
        ControlDotsConnectingRadius = controlDotsConnectingRadius;
    }
}
