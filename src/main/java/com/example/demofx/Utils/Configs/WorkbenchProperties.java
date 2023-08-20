package com.example.demofx.Utils.Configs;

import java.util.HashMap;
import java.util.Properties;

public class WorkbenchProperties {

    private static WorkbenchProperties workbenchProperties;

    private HashMap<String, Object> ProjectProperties;

    private boolean isAllRadiusDrawing;

    private boolean isControlDotsConnecting;

    private int ControlDotsConnectingRadius;

    public WorkbenchProperties() {
        this.ProjectProperties = new HashMap<>();
        addProperty("isWayPointsNamed", false);
        addProperty("isAllRadiusDraw", false);
        addProperty("isCDConnecting", false);
        addProperty("CDConnectRadius", 14);
    }

    public static synchronized WorkbenchProperties getInstance() {
        if (workbenchProperties == null) {
            workbenchProperties = new WorkbenchProperties();
        }
        return workbenchProperties;
    }

    public void addProperty(String key, Object value){
        ProjectProperties.put(key, value);
    }

    public Object getPropertyByName(String key){
        return ProjectProperties.get(key);
    }

    public boolean changeProperty(String key, Object newValue){
        if(ProjectProperties.containsKey(key)) {
            ProjectProperties.put(key, newValue);
            return true;
        }
        return false;
    }

}
