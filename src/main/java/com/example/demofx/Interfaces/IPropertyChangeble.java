package com.example.demofx.Interfaces;

import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public interface IPropertyChangeble {

    SortedMap<String, String> ModelErrorsCheck(SortedMap<String, String> messageMap);

    NodeModelContainer getTreePropertyNode(ModelTreeProvider provider);

    NodeModelContainer getGraphPropertyNode();

    NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider);

    String getTreeItemName();

    void getPropertiesFromNode(VBox vBox);

    void setPropertiesFromModalWindow(String key, Object value);

    Object getPropertyToModalWindow(String key);

    boolean removeObjectWithId(String itemId);

    String dataToString();

}
