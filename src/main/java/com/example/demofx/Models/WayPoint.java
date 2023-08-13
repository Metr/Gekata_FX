package com.example.demofx.Models;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Interfaces.ISpecialSpot;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Events.EventContextController;
import com.example.demofx.Utils.Generators.PropertyItemGenerator;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.geom.Point2D;
import java.util.*;

public class WayPoint implements ISpecialSpot, IGraphPrimitive, IPropertyChangeble {

    //model variables
    private String ItemId;
    private double x_pos;
    private double y_pos;
    private double radius;

    private String fromLevelId;

    private String toLevelId;


    // data variables


    //visualisation variables

    private Circle wayPointItemShape;
    private Circle wayPointRadiusShape;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;


    public WayPoint(int x_pos, int y_pos, double radius, String fromLevelId) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.radius = radius;

        this.fromLevelId = fromLevelId;
        this.toLevelId = "";

        this.ItemId = UUID.randomUUID().toString();

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

    @Override
    public ArrayList<Point2D> GetPointsToConnect() {
        return null;
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
            if(WorkbenchProperties.getInstance().isAllRadiusDrawing()){
                wayPointItemShape.setFill(Color.CYAN);

                wayPointRadiusShape.setRadius(radius);
                wayPointRadiusShape.setFill(Color.AQUAMARINE);
                wayPointRadiusShape.setOpacity(0.3);
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
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point X", String.valueOf(this.getX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point Y", String.valueOf(this.getY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("trust radius", String.valueOf(this.getRadius()), provider));
        if (Objects.equals(this.toLevelId, ""))
            resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyListControl("go to level"
                    , "NaN"
                    , HouseProject.getInstance().getBuilding().getIdNameLevels()
                    , provider));
        else
            resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyListControl("go to level"
                    , HouseProject.getInstance().getBuilding().getLevelNameWithId(this.toLevelId)
                    , HouseProject.getInstance().getBuilding().getIdNameLevels()
                    , provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point X", String.valueOf(this.getX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point Y", String.valueOf(this.getY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("trust radius", String.valueOf(this.getRadius()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreePropertyListControl("go to level"
                , HouseProject.getInstance().getBuilding().getLevelNameWithId(this.fromLevelId)
                , HouseProject.getInstance().getBuilding().getIdNameLevels()
                , provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public String getTreeItemName() {
        String from, to;
        from = "" + HouseProject.getInstance().getBuilding().getLevelNameWithId(fromLevelId);
        to = "" + HouseProject.getInstance().getBuilding().getLevelNameWithId(toLevelId);
        return "go to " + to + " from " + from;
    }

    @Override
    public void setPropertiesFromModalWindow(String key, Object value) {

    }

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
                            case "point X":
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
                        HashMap<String, String> levelsMap = HouseProject.getInstance().getBuilding().getIdNameLevels();
                        if (levelsMap.containsValue(value)) {
                            Set<String> keys = levelsMap.keySet();
                            for (String key : keys)
                                if (levelsMap.get(key).toString().equals(value))
                                    this.toLevelId = key;
                        }

                    }
                    break;
                }
            }
        }
        System.out.println(this.toLevelId);
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
            }

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
