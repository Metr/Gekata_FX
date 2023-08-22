package com.example.demofx.Models.Basic;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Interfaces.ISpecialSpot;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Events.EventContextController;
import com.example.demofx.Utils.Fabrics.ErrorCounterFabric;
import com.example.demofx.Utils.Generators.PropertyItemGenerator;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class WayPoint implements ISpecialSpot, IGraphPrimitive, IPropertyChangeble {

    //model variables
    private String ItemId;

    private String Name;

    private double x_pos;
    private double y_pos;
    private double radius;

    private String fromLevelId;

    private WayPoint finishWayPoint;
    // data variables


    //visualisation variables

    private Circle wayPointItemShape;
    private Circle wayPointRadiusShape;

    private Point2D graphPoint;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;


    public WayPoint(int x_pos, int y_pos, double radius, String fromLevelId) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.radius = radius;

        this.fromLevelId = fromLevelId;
//        this.toLevelId = "";
//        this.toWayPointId = "";

        this.ItemId = UUID.randomUUID().toString();
        this.Name = "no name waypoint " + this.ItemId;

        this.wayPointItemShape = new Circle(x_pos, y_pos, 10);
        wayPointItemShape.setStrokeWidth(3);
        wayPointItemShape.setStroke(Color.BLACK);
        wayPointItemShape.setFill(Color.TURQUOISE);

        this.wayPointRadiusShape = new Circle(x_pos, y_pos, this.radius);
        wayPointRadiusShape.setRadius(0);
        wayPointRadiusShape.setFill(Color.AQUAMARINE);
        wayPointRadiusShape.setStroke(Color.DEEPSKYBLUE);
        wayPointRadiusShape.setStrokeWidth(2);
        wayPointRadiusShape.setOpacity(0.9);

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFromLevelId() {
        return fromLevelId;
    }

    @Override
    public double getX() {
        return x_pos;
    }

    @Override
    public double getY() {
        return y_pos;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public WayPoint getFinishWayPoint() {
        return finishWayPoint;
    }

    public void setFinishWayPoint(WayPoint finishWayPoint) {
        this.finishWayPoint = finishWayPoint;
    }

    public Point2D getGraphPoint() {
        return graphPoint;
    }

    @Override
    public ArrayList<Point2D> GetPointsToConnect() {
        return null;
    }

    public Node GetDrowableGraphElementAt(double x, double y) {
        Group parent = new Group();

        this.graphPoint = new Point2D.Double(x, y);

        Circle circle = new Circle();
        circle.setCenterX(graphPoint.getX());
        circle.setCenterY(graphPoint.getY());
        circle.setOnMousePressed(OnMousePressedOnGraphItemEventHandler);
        circle.setRadius(15);
        circle.setFill(Color.CYAN);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);

        javafx.scene.control.Label label = new Label(this.Name);
        label.setTranslateX(x + 15);
        label.setTranslateY(y - 20);

        if (finishWayPoint == null) {
            circle.setFill(Color.RED);
        }


        parent.getChildren().add(circle);
        parent.getChildren().add(label);
        return parent;
    }


    @Override
    public Node GetDrowableElement() {
        Group parent = new Group();

        wayPointItemShape.setCenterX(this.x_pos);
        wayPointItemShape.setCenterY(this.y_pos);

        wayPointRadiusShape.setCenterX(this.x_pos);
        wayPointRadiusShape.setCenterY(this.y_pos);

        wayPointItemShape.setOnMousePressed(OnMousePressedEventHandler);
        wayPointItemShape.setOnMouseDragged(OnMouseDraggedEventHandler);
        wayPointItemShape.setOnMouseReleased(OnMouseReleasedEventHandler);


        if (HouseProject.getInstance().getSelectedItem() != null) {
            //if element will be selected
            if (HouseProject.getInstance().getSelectedItem().getGraphModel().GetId() == this.GetId()) {
                wayPointItemShape.setRadius(13);
                wayPointItemShape.setFill(Color.CYAN);

                wayPointRadiusShape.setRadius(radius);
                wayPointRadiusShape.setFill(Color.AQUAMARINE);
                wayPointRadiusShape.setOpacity(0.3);
            } else {
                wayPointItemShape.setRadius(10);
                wayPointItemShape.setStrokeWidth(3);
                wayPointItemShape.setStroke(Color.BLACK);
                wayPointItemShape.setFill(Color.TURQUOISE);

                wayPointRadiusShape.setRadius(0);
                wayPointRadiusShape.setFill(Color.AQUAMARINE);
                wayPointRadiusShape.setStroke(Color.DEEPSKYBLUE);
                wayPointRadiusShape.setStrokeWidth(2);
                wayPointRadiusShape.setOpacity(0.9);
            }

            //if radius visible is ON
            if ((boolean) WorkbenchProperties.getInstance().getPropertyByName("isAllRadiusDraw")) {
                wayPointItemShape.setFill(Color.CYAN);

                wayPointRadiusShape.setRadius(radius);
                wayPointRadiusShape.setFill(Color.AQUAMARINE);
                wayPointRadiusShape.setOpacity(0.3);
            }

            if ((boolean) WorkbenchProperties.getInstance().getPropertyByName("isWayPointsNamed")) {
                Label label = new Label(this.Name);
                label.setTranslateX(this.x_pos + 14);
                label.setTranslateY(this.y_pos - 14);
                parent.getChildren().add(label);
            }

            if (finishWayPoint == null) {
                wayPointItemShape.setFill(Color.RED);
            }
        }

        parent.getChildren().add(wayPointRadiusShape);
        parent.getChildren().add(wayPointItemShape);
        return parent;
    }

    @Override
    public NodeModelContainer GetContainer() {
        NodeModelContainer container = new NodeModelContainer(
                this.getTreePropertyNode(EventContextController.getModelTreeProvider()).getPropertyNode(),
                this, this);
        return container;
    }

    @Override
    public String GetId() {
        return this.ItemId;
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeButton("delete item", event ->  HouseProject.getInstance().RemoveObjectWithID(this.ItemId)));
        resultContainer.getChildren().add(PropertyItemGenerator.
                generateTreeRedrawOnUnFocusPropertyControl("name", this.Name, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point X", String.valueOf(this.getX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point Y", String.valueOf(this.getY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("trust radius", String.valueOf(this.getRadius()), provider));
        if (finishWayPoint == null)
            resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyListControl("go to point"
                    , "NaN"
                    , HouseProject.getInstance().getBuilding().getIdNameWaypoints()
                    , provider));
        else
            resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyListControl("go to point"
                    , finishWayPoint.getName()
                    , HouseProject.getInstance().getBuilding().getIdNameWaypoints()
                    , provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        return new NodeModelContainer(resultContainer, this, this);
    }

    public NodeModelContainer getGraphPropertyNode() {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateGraphRedrawOnChangePropertyControl("name", this.Name, EventContextController.getModelTreeProvider()));
        if (finishWayPoint == null)
            resultContainer.getChildren().add(PropertyItemGenerator.generateGraphPropertyListControl("go to point"
                    , "NaN"
                    , HouseProject.getInstance().getBuilding().getIdNameWaypoints()
                    , EventContextController.getModelTreeProvider()));
        else
            resultContainer.getChildren().add(PropertyItemGenerator.generateGraphPropertyListControl("go to point"
                    , finishWayPoint.getName()
                    , HouseProject.getInstance().getBuilding().getIdNameWaypoints()
                    , EventContextController.getModelTreeProvider()));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public String getTreeItemName() {
        return this.Name;

    }

    @Override
    public void setPropertiesFromModalWindow(String key, Object value) {    }

    @Override
    public Object getPropertyToModalWindow(String key) {
        return null;
    }

    @Override
    public void getPropertiesFromNode(VBox vBox) {
        //по свойствам
        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;
            //по контейнерам лейбл+текстбокс
            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node subNode : hBox.getChildren()) {
                if (subNode.getClass() == TextField.class || subNode.getClass() == ChoiceBox.class) {
                    if (subNode.getClass() == TextField.class) {
                        TextField text = (TextField) subNode;
                        switch (text.getId()) {
                            case "name":
                                this.Name = text.getText();
                                break;
                            case "point X":
                                //TODO check text value
                                this.x_pos = Double.valueOf(text.getText());
                                break;
                            case "point Y":
                                this.y_pos = Double.valueOf(text.getText());
                                break;
                            case "trust radius":
                                this.radius = Double.valueOf(text.getText());
                                break;
                            case "go to level":
                                //y2 = Double.valueOf(text.getText());
                                break;
                        }
                    }
                    if (subNode.getClass() == ChoiceBox.class) {
                        ChoiceBox<String> choiceBox = (ChoiceBox<String>) subNode;
                        String value = choiceBox.getValue();
                        HashMap<String, String> waypointMap = HouseProject.getInstance().getBuilding().getIdNameWaypoints();
                        if (waypointMap.containsValue(value)) {
                            Set<String> keys = waypointMap.keySet();
                            for (String key : keys)
                                if (waypointMap.get(key).equals(value))
                                    this.finishWayPoint = HouseProject.getInstance().getBuilding().getWayPointWithId(key);
                        }

                    }
                    break;
                }
            }
        }
        //System.out.println(this.toLevelId);
    }

    @Override
    public SortedMap<String, String> ModelErrorsCheck(SortedMap<String, String> messageMap) {

        //////////////////////////////////////////errors
        if(this.Name.isEmpty())
            messageMap.put("00004-"+ ErrorCounterFabric.getCounter(), "Way Point name in empty or null");
        if(this.x_pos <= 0 || this.y_pos <= 0)
            messageMap.put("00005-"+ ErrorCounterFabric.getCounter(), "Way Point \'" + this.Name + "\' pos_x or/and pos_y <= 0");
        if(this.radius <= 0)
            messageMap.put("00006-"+ ErrorCounterFabric.getCounter(), "Way Point \'" + this.Name + "\' radius <= 0");


        //////////////////////////////////////////warnings
        if(this.finishWayPoint == null)
            messageMap.put("10002-"+ ErrorCounterFabric.getCounter(), "Way Point \'" + this.Name + "\' dont connected with any Way Point");

        return messageMap;
    }

    @Override
    public boolean removeObjectWithId(String itemId) {
        return false;
    }

    private final EventHandler<MouseEvent> OnMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            if (t.getSource() instanceof Circle) {
                Circle circle = ((Circle) (t.getSource()));
                orgTranslateX = circle.getCenterX();
                orgTranslateY = circle.getCenterY();

                NodeModelContainer container = GetContainer();
                HouseProject.getInstance().setSelectedItem(container);
//                EventContextController.getTopMenuProvider().BroadcastDrawWayPointsGraphCommand();
            }

        }
    };

    private final EventHandler<MouseEvent> OnMousePressedOnGraphItemEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            NodeModelContainer container = GetContainer();
            HouseProject.getInstance().setSelectedItem(container);
            EventContextController.getTopMenuProvider().BroadcastDrawWayPointsGraphCommand(container);
        }
    };

    private final EventHandler<MouseEvent> OnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            if (t.getSource() instanceof Circle) {
                Circle p = ((Circle) (t.getSource()));
                p.setCenterX(newTranslateX);
                p.setCenterY(newTranslateY);
            }
        }
    };

    private final EventHandler<MouseEvent> OnMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            if (t.getSource() instanceof Circle) {
                x_pos = newTranslateX;
                y_pos = newTranslateY;

            }

            EventContextController.RenderAll();
        }
    };


    @Override
    public String dataToString() {
        return this.ItemId;
    }


}
