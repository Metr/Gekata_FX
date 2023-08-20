package com.example.demofx.Models.Basic;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Events.EventContextController;
import com.example.demofx.Utils.Generators.PropertyItemGenerator;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.UUID;

public class Level implements IPropertyChangeble, IGraphPrimitive {

    private String ItemId;

    private ArrayList<IGraphPrimitive> Walls;

    private ArrayList<IGraphPrimitive> CurveWalls;

    private ArrayList<IGraphPrimitive> WayPoints;

    private ArrayList<IGraphPrimitive> InterestPoints;
    private String Name;

    ///VIEW_DATA////////////////////////////

    Circle levelShape;
    Label levelNameLabel;

    public Level() {
        this.Name = "unknown level " + HouseProject.getInstance().getBuilding().getLevels().size();
        this.Walls = new ArrayList<IGraphPrimitive>();
        this.WayPoints = new ArrayList<IGraphPrimitive>();
        this.InterestPoints = new ArrayList<IGraphPrimitive>();

//        for (int i = 0; i < 30; i += 2)
//            this.Walls.add(new BasicWall(i, i, i + 100, i));
    }

    public Level(String name) {
        this.ItemId = UUID.randomUUID().toString();
        this.Name = name;
        this.levelShape = new Circle();
        this.levelNameLabel = new Label(this.Name);
        this.Walls = new ArrayList<IGraphPrimitive>();
        this.CurveWalls = new ArrayList<IGraphPrimitive>();
        this.WayPoints = new ArrayList<IGraphPrimitive>();
        this.InterestPoints = new ArrayList<IGraphPrimitive>();
//        for (int i = 0; i < 300; i += 10)
//            this.Walls.add(new BasicWall(i, i, i + 100, i));
    }


    public Node getLevelNode() {
        Pane pane = new Pane();
        for (IGraphPrimitive wall : Walls) {
            pane.getChildren().add(wall.GetDrowableElement());
        }
        for (IGraphPrimitive curve : CurveWalls) {
            pane.getChildren().add(curve.GetDrowableElement());
        }
        for (IGraphPrimitive wayPoint : WayPoints) {
            pane.getChildren().add(wayPoint.GetDrowableElement());
        }
        for (IGraphPrimitive interest : InterestPoints) {
            pane.getChildren().add(interest.GetDrowableElement());
        }
        return pane;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public ArrayList<IGraphPrimitive> getWalls() {
        return Walls;
    }

    public void setWalls(ArrayList<IGraphPrimitive> walls) {
        Walls = walls;
    }

    public ArrayList<IGraphPrimitive> getWayPoints() {
        return WayPoints;
    }

    public ArrayList<WayPoint> getWayPointsToGraph(){
        ArrayList<WayPoint> result = new ArrayList<>();

        for(int i = 0; i < WayPoints.size(); i++)
            result.add((WayPoint) WayPoints.get(i));

        return result;
    }

    public void setWayPoints(ArrayList<IGraphPrimitive> wayPoints) {
        WayPoints = wayPoints;
    }

    public ArrayList<IGraphPrimitive> getCurveWalls() {
        return CurveWalls;
    }

    public void setCurveWalls(ArrayList<IGraphPrimitive> curveWalls) {
        CurveWalls = curveWalls;
    }

    public ArrayList<IGraphPrimitive> getInterestPoints() {
        return InterestPoints;
    }

    public void setInterestPoints(ArrayList<IGraphPrimitive> interestPoints) {
        InterestPoints = interestPoints;
    }

    public Circle getLevelShape() {
        return levelShape;
    }

    @Override
    public NodeModelContainer getGraphPropertyNode() {
        return null;
    }

    public void setLevelShape(double x, double y) {
        this.levelShape.setCenterX(x);
        this.levelShape.setCenterY(y);
    }

    public Label getLevelNameLabel() {
        return levelNameLabel;
    }

    public void setLevelNameLabel(String newName) {
        this.levelNameLabel.setText(newName);
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();
        resultContainer.getChildren().add(PropertyItemGenerator.
                generateTreeRedrawOnUnFocusPropertyControl("name", this.Name, provider));
        return new NodeModelContainer(resultContainer, this, this);
    }

    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        return null;
    }


    @Override
    public String getTreeItemName() {
        return Name;
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

        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;

            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node label : hBox.getChildren()) {
                if (label.getClass() == TextField.class) {
                    TextField textField = (TextField) label;
                    this.Name = textField.getText();
                }
            }
        }

    }

    @Override
    public String dataToString() {
        return "name = " + this.Name;
    }

    @Override
    public ArrayList<Point2D> GetPointsToConnect() {
        ArrayList<Point2D> result = new ArrayList<>();

        for (IGraphPrimitive wall : Walls) {
            result.addAll(wall.GetPointsToConnect());
        }
        for (IGraphPrimitive curve : CurveWalls) {
            result.addAll(curve.GetPointsToConnect());
        }

        return result;
    }

    @Override
    public Node GetDrowableElement() {
        Group parent = new Group();

        levelShape.setFill(Color.AQUA);
        levelShape.setStroke(Color.BLACK);
        levelShape.setOpacity(0.5);
        levelShape.setStrokeWidth(3);
        levelShape.setRadius(20);

        levelNameLabel.setText(this.Name);
        levelNameLabel.setTranslateX(levelShape.getCenterX()+22);
        levelNameLabel.setTranslateY(levelShape.getCenterY()-22);

        parent.getChildren().add(levelShape);
        parent.getChildren().add(levelNameLabel);
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
    public SortedMap<String, String> ModelErrorsCheck(SortedMap<String, String> messageMap) {

        //////////////////////////////////////////errors
        if(this.Name.isEmpty())
            messageMap.put("00003", "level name in empty or null");
        if(this.WayPoints.isEmpty())
            messageMap.put("00004", "level \'" + this.Name + "\' dont contains any Way Point object");
        if(this.Walls.isEmpty() && this.CurveWalls.isEmpty())
            messageMap.put("00005", "level \'" + this.Name + "\' dont contains any Wall object");


        //////////////////////////////////////////warnings
        if(this.InterestPoints.isEmpty())
            messageMap.put("10001", "level \'" + this.Name + "\' dont contains any Interest Point object");


        //////////////////////////////////////////inner model objects
        for(IGraphPrimitive point : WayPoints)
            point.GetContainer().getPropertyModel().ModelErrorsCheck(messageMap);
        for(IGraphPrimitive point : InterestPoints)
            point.GetContainer().getPropertyModel().ModelErrorsCheck(messageMap);
        for(IGraphPrimitive point : Walls)
            point.GetContainer().getPropertyModel().ModelErrorsCheck(messageMap);
        for(IGraphPrimitive point : CurveWalls)
            point.GetContainer().getPropertyModel().ModelErrorsCheck(messageMap);


        return messageMap;
    }

    @Override
    public String GetId() {
        return this.ItemId;
    }
}
