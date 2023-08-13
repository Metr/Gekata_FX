package com.example.demofx.Utils.Containers;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IPropertyChangeble;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NodeModelContainer {


    Node PropertyNode;
    IPropertyChangeble PropertyModel;
    IGraphPrimitive GraphModel;

//    public NodeModelContainer() {
//
//    }

    public NodeModelContainer(Node PNode, IPropertyChangeble PModel, IGraphPrimitive GModel) {
        this.PropertyNode = PNode;
        this.PropertyModel = PModel;
        this.GraphModel = GModel;
    }

    public Node getPropertyNode() {
        return PropertyNode;
    }

    public void setPropertyNode(Node propertyNode) {
        PropertyNode = propertyNode;
    }

    public IPropertyChangeble getPropertyModel() {
        return PropertyModel;
    }

    public void setPropertyModel(IPropertyChangeble propertyModel) {
        PropertyModel = propertyModel;
    }

    public IGraphPrimitive getGraphModel() {
        return GraphModel;
    }

    public void setGraphModel(IGraphPrimitive graphModel) {
        GraphModel = graphModel;
    }
}
