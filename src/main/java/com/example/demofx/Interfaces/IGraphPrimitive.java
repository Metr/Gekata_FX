package com.example.demofx.Interfaces;

import com.example.demofx.Utils.Containers.NodeModelContainer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface IGraphPrimitive {

    void InitGraphData();

    Node GetDrowableElement();

    NodeModelContainer GetContainer();

    int GetId();


    ArrayList<Point2D> GetPointsToConnect();

    EventHandler<MouseEvent> OnMousePressedEventHandler = null;

    EventHandler<MouseEvent> OnMouseDraggedEventHandler = null;

    EventHandler<MouseEvent> OnMouseReleasedEventHandler = null;

}
