package com.example.demofx.Models.Basic;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Events.EventContextController;
import com.example.demofx.Utils.Fabrics.ErrorCounterFabric;
import com.example.demofx.Utils.Generators.PropertyItemGenerator;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.SortedMap;
import java.util.UUID;

public class QuadCurveWall implements IGraphPrimitive, IPropertyChangeble {

    private final String ItemId;

    private Point2D startPoint;
    private Point2D endPoint;

    private Point2D controlPoint;

    private QuadCurve quadCurve;

    private String lastChange;
    private boolean isBeengChanged = false;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private Circle startPointShape;
    private Circle endPointShape;
    private Circle controlPointShape;

    private static GregorianCalendar date;

    {
        date = new GregorianCalendar();
    }


    public QuadCurveWall(double x1, double y1, double x2, double y2) {
        this.startPoint = new Point2D.Double(x1, y1);
        this.endPoint = new Point2D.Double(x2, y2);
        this.controlPoint = new Point((int)(x2-x1), (int)(y2-y1));

        this.startPointShape = new Circle(startPoint.getX(), startPoint.getY(), 0);
        this.endPointShape = new Circle(endPoint.getX(), endPoint.getY(), 0);
        this.controlPointShape = new Circle(controlPoint.getX(), controlPoint.getY(), 0);

        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(),controlPoint.getY(),
                endPoint.getX(), endPoint.getY());
        this.ItemId = UUID.randomUUID().toString();
        this.lastChange = "last change: " + date.getTime().toString().substring(0, 19);
    }

    public QuadCurveWall(double x1, double y1, double control_x, double control_y, double x2, double y2) {
        this.startPoint = new Point2D.Double(x1, y1);
        this.endPoint = new Point2D.Double(x2, y2);
        this.controlPoint = new Point((int)(control_x), (int)(control_y));

        this.startPointShape = new Circle(startPoint.getX(), startPoint.getY(), 0);
        this.endPointShape = new Circle(endPoint.getX(), endPoint.getY(), 0);
        this.controlPointShape = new Circle(controlPoint.getX(), controlPoint.getY(), 0);

        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(),controlPoint.getY(),
                endPoint.getX(), endPoint.getY());

        this.quadCurve.setStrokeWidth(3);
        this.quadCurve.setStroke(Color.BLACK);
        this.quadCurve.setFill(Color.TRANSPARENT);

        this.ItemId = UUID.randomUUID().toString();
        this.lastChange = "last change: " + date.getTime().toString().substring(0, 19);
    }

    public void setStartPoint(Point2D startPoint) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.startPoint = startPoint;
        this.startPointShape = new Circle(startPoint.getX(), startPoint.getY(), 0);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void setStartPoint(double x, double y) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.startPoint = new Point2D.Double(x, y);
        this.startPointShape.setCenterX(x);
        this.startPointShape.setCenterY(y);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void setEndPoint(Point2D endPoint) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.endPoint = endPoint;
        this.endPointShape = new Circle(endPoint.getX(), endPoint.getY(), 0);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public void setEndPoint(double x, double y) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.endPoint = new Point2D.Double(x, y);
        this.endPointShape.setCenterX(x);
        this.endPointShape.setCenterY(y);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }


    public void setControlPoint(double x, double y) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.controlPoint = new Point2D.Double(x, y);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }


    public Point2D getControlPoint() {
        return controlPoint;
    }

    public void setControlPoint(Point2D controlPoint) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.controlPoint = controlPoint;
        this.controlPointShape = new Circle(controlPoint.getX(), controlPoint.getY(), 0);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    public double getStartX() {
        return startPoint.getX();
    }

    public double getStartY() {
        return startPoint.getY();
    }

    public double getEndX() {
        return endPoint.getX();
    }

    public double getEndY() {
        return endPoint.getY();
    }

    public double getControlX() {
        return controlPoint.getX();
    }

    public double getControlY() {
        return controlPoint.getY();
    }

    public void setQuadCurve(QuadCurve newCurve) {
        lastChange = "last change: " + date.getTime().toString().substring(0, 19);
        this.quadCurve = new QuadCurve(startPoint.getX(), startPoint.getY(),
                controlPoint.getX(), controlPoint.getY(), endPoint.getX(), endPoint.getY());
    }


    @Override
    public ArrayList<Point2D> GetPointsToConnect() {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        list.add(startPoint);
        list.add(endPoint);
        return list;
    }

    @Override
    public NodeModelContainer getGraphPropertyNode() {
        return null;
    }

    @Override
    public Node GetDrowableElement() {
        Group parent = new Group();
        quadCurve.setOnMousePressed(OnMousePressedEventHandler);
        quadCurve.setOnMouseDragged(OnMouseDraggedEventHandler);
        quadCurve.setOnMouseReleased(OnMouseReleasedEventHandler);

        if (HouseProject.getInstance().getSelectedItem() != null) {
            if (HouseProject.getInstance().getSelectedItem().getGraphModel().GetId() == this.GetId()) {

                this.quadCurve.setStrokeWidth(3);
                this.quadCurve.setStroke(Color.BLACK);
                this.quadCurve.setFill(Color.TRANSPARENT);

                startPointShape.setRadius(5);
                startPointShape.setFill(Color.CADETBLUE);
                startPointShape.setStrokeWidth(2);
                startPointShape.setOnMousePressed(OnMousePressedEventHandler);
                startPointShape.setOnMouseDragged(OnMouseDraggedEventHandler);
                startPointShape.setOnMouseReleased(OnMouseReleasedEventHandler);

                endPointShape.setRadius(5);
                endPointShape.setFill(Color.CADETBLUE);
                endPointShape.setStrokeWidth(2);
                endPointShape.setOnMousePressed(OnMousePressedEventHandler);
                endPointShape.setOnMouseDragged(OnMouseDraggedEventHandler);
                endPointShape.setOnMouseReleased(OnMouseReleasedEventHandler);

                controlPointShape.setRadius(5);
                controlPointShape.setFill(Color.CADETBLUE);
                controlPointShape.setStrokeWidth(2);
                controlPointShape.setOnMousePressed(OnMousePressedEventHandler);
                controlPointShape.setOnMouseDragged(OnMouseDraggedEventHandler);
                controlPointShape.setOnMouseReleased(OnMouseReleasedEventHandler);
            }
            else {
                this.quadCurve.setStrokeWidth(3);
                this.quadCurve.setStroke(Color.BLACK);
                this.quadCurve.setFill(Color.TRANSPARENT);
                startPointShape.setRadius(0);
                endPointShape.setRadius(0);
                controlPointShape.setRadius(0);
            }
        }

        parent.getChildren().add(quadCurve);
        parent.getChildren().add(startPointShape);
        parent.getChildren().add(endPointShape);
        parent.getChildren().add(controlPointShape);
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
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point_1 X", String.valueOf(this.getStartX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point_1 Y", String.valueOf(this.getStartY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point_2 X", String.valueOf(this.getEndX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("point_2 Y", String.valueOf(this.getEndY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("control X", String.valueOf(this.getControlX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnChangePropertyControl("control Y", String.valueOf(this.getControlY()), provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeButton("delete item", event ->  HouseProject.getInstance().RemoveObjectWithID(this.ItemId)));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_1 X", String.valueOf(this.getStartX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_1 Y", String.valueOf(this.getStartY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_2 X", String.valueOf(this.getEndX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("point_2 Y", String.valueOf(this.getEndY()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("control X", String.valueOf(this.getControlX()), provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateWorkbenchPropertyControl("control Y", String.valueOf(this.getControlY()), provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public String getTreeItemName() {
        if (isBeengChanged) {
            date = new GregorianCalendar();
            lastChange = "last change: " + date.getTime().toString().substring(0, 19);
            return "Curve (" + lastChange + ")";
        } else return "Curve";
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
        isBeengChanged = true;

        double x1 = startPoint.getX(), y1 = startPoint.getY();
        double x2 = endPoint.getX(), y2 = endPoint.getY();
        double control_x = controlPoint.getX(), control_y = controlPoint.getY();

        //по свойствам
        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;
            //по контейнерам лейбл+текстбокс
            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node subNode : hBox.getChildren()) {
                if (subNode.getClass() == TextField.class) {
                    TextField text = (TextField) subNode;
                    switch (text.getId()) {
                        case "point_1 X":
                            x1 = Double.valueOf(text.getText());
                            break;
                        case "point_1 Y":
                            y1 = Double.valueOf(text.getText());
                            break;
                        case "point_2 X":
                            x2 = Double.valueOf(text.getText());
                            break;
                        case "point_2 Y":
                            y2 = Double.valueOf(text.getText());
                            break;
                        case "control X":
                            control_x = Double.valueOf(text.getText());
                            break;
                        case "control Y":
                            control_y = Double.valueOf(text.getText());
                            break;
                    }
                    break;
                }
            }
        }
        this.setControlPoint(new Point2D.Double(control_x, control_y));
        this.setStartPoint(new Point2D.Double(x1, y1));
        this.setEndPoint(new Point2D.Double(x2, y2));

    }

    @Override
    public String dataToString() {
        return "x1=" + startPoint.getX() + " y1=" + startPoint.getY() + ", x2=" + endPoint.getX() + " y2=" + endPoint.getY();
    }

    @Override
    public SortedMap<String, String> ModelErrorsCheck(SortedMap<String, String> messageMap) {
        //////////////////////////////////////////errors
        if(this.startPoint.getX() < 0 || this.startPoint.getY() < 0)
            messageMap.put("00012-"+ ErrorCounterFabric.getCounter(), "Wall start point contains coords < 0");
        if(this.endPoint.getX() < 0 || this.endPoint.getY() < 0)
            messageMap.put("00013-"+ ErrorCounterFabric.getCounter(), "Wall end point contains coords < 0");
        if(this.controlPoint.getX() < 0 || this.controlPoint.getY() < 0)
            messageMap.put("00014-"+ ErrorCounterFabric.getCounter(), "Wall control point contains coords < 0");

        //////////////////////////////////////////warnings

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
            }
            if (t.getSource() instanceof QuadCurve) {
                Node p = ((Node) (t.getSource()));
                orgTranslateX = p.getTranslateX();
                orgTranslateY = p.getTranslateY();
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
            } else {
                Node p = ((Node) (t.getSource()));
                p.setTranslateX(newTranslateX);
                p.setTranslateY(newTranslateY);
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
                //если (новые коорды - сдвиг - положение стартовой точки) меньше единицы (поправка на округление),
                //то считаем что распознали точку
                if (Math.abs((newTranslateX - offsetX) - startPoint.getX()) < 1 && Math.abs((newTranslateY - offsetY) - startPoint.getY()) < 1) {
                    if ((boolean)WorkbenchProperties.getInstance().getPropertyByName("isCDConnecting")) {
                        String selectedLevelName = EventContextController.getWorkbenchProvider().getSelectedLevelName();
                        int levelIndex = HouseProject.getInstance().getBuilding().getIndexLevelWithName(selectedLevelName);
                        ArrayList<Point2D> points = HouseProject.getInstance().getBuilding().getLevels().get(levelIndex).GetPointsToConnect();

                        double distX;
                        double distSelfParePointX;
                        double distY;
                        double distSelfParePointY;
                        int connectRadius;
                        for (Point2D point : points) {
                            distX = Math.abs(newTranslateX - point.getX());
                            distY = Math.abs(newTranslateY - point.getY());
                            distSelfParePointX = Math.abs(newTranslateX - endPoint.getX());
                            distSelfParePointY = Math.abs(newTranslateY - endPoint.getY());
                            connectRadius = (int)WorkbenchProperties.getInstance().getPropertyByName("CDConnectRadius");
                            if (distX <= connectRadius && distSelfParePointX > connectRadius
                                    && distY <= connectRadius && distSelfParePointY > connectRadius) {
                                newTranslateX = point.getX();
                                newTranslateY = point.getY();
                                break;
                            }
                        }
                    }
                    setStartPoint(newTranslateX, newTranslateY);
                }
                if (Math.abs((newTranslateX - offsetX) - endPoint.getX()) < 1 && Math.abs((newTranslateY - offsetY) - endPoint.getY()) < 1) {
                    if ((boolean)WorkbenchProperties.getInstance().getPropertyByName("isCDConnecting")) {
                        String selectedLevelName = EventContextController.getWorkbenchProvider().getSelectedLevelName();
                        int levelIndex = HouseProject.getInstance().getBuilding().getIndexLevelWithName(selectedLevelName);
                        ArrayList<Point2D> points = HouseProject.getInstance().getBuilding().getLevels().get(levelIndex).GetPointsToConnect();

                        double distX;
                        double distSelfParePointX;
                        double distY;
                        double distSelfParePointY;
                        int connectRadius;
                        for (Point2D point : points) {
                            distX = Math.abs(newTranslateX - point.getX());
                            distY = Math.abs(newTranslateY - point.getY());
                            distSelfParePointX = Math.abs(newTranslateX - startPoint.getX());
                            distSelfParePointY = Math.abs(newTranslateY - startPoint.getY());
                            connectRadius = (int)WorkbenchProperties.getInstance().getPropertyByName("CDConnectRadius");
                            if (distX <= connectRadius && distSelfParePointX > connectRadius
                                    && distY <= connectRadius && distSelfParePointY > connectRadius) {
                                newTranslateX = point.getX();
                                newTranslateY = point.getY();
                                break;
                            }
                        }
                    }
                    setEndPoint(newTranslateX, newTranslateY);
                }
                if(Math.abs((newTranslateX - offsetX)-controlPoint.getX()) < 1 && Math.abs((newTranslateY - offsetY)-controlPoint.getY()) < 1){
                    setControlPoint(newTranslateX,newTranslateY);
                }
            }
            if (t.getSource() instanceof QuadCurve) {
                {
                    setStartPoint(new Point2D.Double(getStartX() + offsetX, getStartY() + offsetY));
                    setEndPoint(new Point2D.Double(getEndX() + offsetX, getEndY() + offsetY));
                    setControlPoint(new Point2D.Double(getControlX() + offsetX, getControlY() + offsetY));
                    NodeModelContainer container = GetContainer();
                    HouseProject.getInstance().setSelectedItem(container);

                }
            }
            EventContextController.RenderAll();
        }
    };



}
