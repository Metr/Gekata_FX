package com.example.demofx.Models.Basic;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IInterestSpot;
import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Interfaces.ISpecialSpot;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Enums.EnumUtils;
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

import java.awt.geom.Point2D;
import java.util.*;

public class InterestPoint implements IInterestSpot, ISpecialSpot, IGraphPrimitive, IPropertyChangeble {

    private String ItemId;
    private double x_pos;
    private double y_pos;
    private double radius;

    private String description;
    private String objectName;
    private DescriptionTypes contentType;

    //visualisation variables

    private Circle wayPointItemShape;
    private Circle wayPointRadiusShape;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public InterestPoint(double x_pos, double y_pos, double radius) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.radius = radius;

        this.description = "";
        this.objectName = "unknown object";
        this.contentType = DescriptionTypes.TEXT_DESCR;

        this.ItemId = UUID.randomUUID().toString();

        this.wayPointItemShape = new Circle(x_pos, y_pos, 10);
        wayPointItemShape.setStrokeWidth(3);
        wayPointItemShape.setStroke(Color.BLACK);
        wayPointItemShape.setFill(Color.CHARTREUSE);

        this.wayPointRadiusShape = new Circle(x_pos, y_pos, this.radius);
        wayPointRadiusShape.setRadius(0);
        wayPointRadiusShape.setFill(Color.LIME);
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


    public DescriptionTypes getContentType() {
        return contentType;
    }

    public void setContentType(DescriptionTypes contentType) {
        this.contentType = contentType;
    }

    public void setX_pos(double x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(double y_pos) {
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
                wayPointItemShape.setFill(Color.LIME);

                wayPointRadiusShape.setRadius(radius);
                wayPointRadiusShape.setFill(Color.LIME);
                wayPointRadiusShape.setStroke(Color.BLACK);
                wayPointRadiusShape.setOpacity(0.3);
            } else {
                wayPointItemShape.setRadius(10);
                wayPointItemShape.setStrokeWidth(3);
                wayPointItemShape.setStroke(Color.BLACK);
                wayPointItemShape.setFill(Color.LIME);

                wayPointRadiusShape.setRadius(0);
                wayPointRadiusShape.setFill(Color.LIME);
                wayPointRadiusShape.setStroke(Color.BLACK);
                wayPointRadiusShape.setStrokeWidth(2);
                wayPointRadiusShape.setOpacity(0.9);
            }

            //if radius visible is ON
            if ((boolean) WorkbenchProperties.getInstance().getPropertyByName("isAllRadiusDraw")) {
                wayPointItemShape.setFill(Color.LIME);

                wayPointRadiusShape.setRadius(radius);
                wayPointRadiusShape.setFill(Color.LIME);
                wayPointRadiusShape.setStroke(Color.BLACK);
                wayPointRadiusShape.setOpacity(0.3);
            }

            if ((boolean) WorkbenchProperties.getInstance().getPropertyByName("isWayPointsNamed")) {
                Label label = new Label(this.objectName);
                label.setTranslateX(this.x_pos + 14);
                label.setTranslateY(this.y_pos - 14);
                parent.getChildren().add(label);
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
    public NodeModelContainer getGraphPropertyNode() {
        return null;
    }

    @Override
    public String GetId() {
        return this.ItemId;
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeButton("delete item", event ->  HouseProject.getInstance().RemoveObjectWithID(this.ItemId)));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("name", this.objectName, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point X", String.valueOf(this.getX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point Y", String.valueOf(this.getY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("trust radius", String.valueOf(this.getRadius()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateModalWindowControl("content", description, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeDescrTypesControl("content type"
                , contentType.name()
                , provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("name", this.objectName, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point X", String.valueOf(this.getX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point Y", String.valueOf(this.getY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("trust radius", String.valueOf(this.getRadius()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateModalWindowControl("content", description, provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public String getTreeItemName() {
        return objectName;
    }

    @Override
    public void setPropertiesFromModalWindow(String key, Object value) {
        switch (key) {
            case "descr" -> {
                this.description = (String) value;
                break;
            }
            default -> {
                break;
            }
        }
    }

    @Override
    public Object getPropertyToModalWindow(String key) {
        switch (key) {
            case "descr" -> {
                return this.description;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public SortedMap<String, String> ModelErrorsCheck(SortedMap<String, String> messageMap) {

        //////////////////////////////////////////errors
        if(this.objectName.isEmpty())
            messageMap.put("00007-"+ ErrorCounterFabric.getCounter(), "Interest Point name in empty or null");
        if(this.x_pos <= 0 || this.y_pos <= 0)
            messageMap.put("00008-"+ ErrorCounterFabric.getCounter(), "Interest Point \'" + this.objectName + "\' pos_x or/and pos_y <= 0");
        if(this.radius <= 0)
            messageMap.put("00009-"+ ErrorCounterFabric.getCounter(), "Interest Point \'" + this.objectName + "\' radius <= 0");


        //////////////////////////////////////////warnings
        if(this.description.isEmpty())
            messageMap.put("10003-"+ ErrorCounterFabric.getCounter(), "Interest Point \'" + this.objectName + "\' description is empty");

        return messageMap;
    }


    @Override
    public boolean removeObjectWithId(String itemId) {
        return false;
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
                                this.objectName = text.getText();
                                break;
                            case "point X":
                                this.x_pos = Double.valueOf(text.getText());
                                break;
                            case "point Y":
                                this.y_pos = Double.valueOf(text.getText());
                                break;
                            case "trust radius":
                                this.radius = Double.valueOf(text.getText());
                                break;
//                            case "content":
//
//                                break;
                        }
                    }
                    if (subNode.getClass() == ChoiceBox.class) {
                        ChoiceBox<String> choiceBox = (ChoiceBox<String>) subNode;
                        String value = choiceBox.getValue();
                        switch (value){
                            case "TEXT_DESCR":
                                this.contentType = DescriptionTypes.TEXT_DESCR;
                                break;
                            case "GRID_DESCR":
                                this.contentType = DescriptionTypes.GRID_DESCR;
                                break;
                            case "CHAT_DESCR":
                                this.contentType = DescriptionTypes.CHAT_DESCR;
                                break;
                            default: break;
                        }


                    }
                    break;
                }
            }
        }
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

    @Override
    public Map<String, Object> GetDescription() {
        return null;
    }

    @Override
    public void SetDescription(Map<String, Object> map) {

    }
}
