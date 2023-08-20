package com.example.demofx.Models.Basic;

import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Events.EventContextController;
import com.example.demofx.Utils.Generators.PropertyItemGenerator;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

public class Building implements IPropertyChangeble, IGraphPrimitive {

    private String name;
    private String adress;
    private String google_adress;
    private String yandex_adress;
    private String osm_adress;

    private String twoGis_adress;

    private ArrayList<Level> Levels;

    public Building(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public Building() {
        this.name = "default_building_name";
        this.adress = "default_adress";
        this.google_adress = "g_a";
        this.yandex_adress = "y_a";
        this.osm_adress = "o_a";
        this.twoGis_adress = "2_a";

        this.Levels = new ArrayList<Level>();
    }

    public Building(String name, String adress, String google_adress, String yandex_adress, String osm_adress, String twoGis_adress) {
        this.name = name;
        this.adress = adress;
        this.google_adress = google_adress;
        this.yandex_adress = yandex_adress;
        this.osm_adress = osm_adress;
        this.twoGis_adress = twoGis_adress;
    }

    @Override
    public NodeModelContainer getTreePropertyNode(ModelTreeProvider provider) {
        VBox resultContainer = new VBox();

        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("name", this.name, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("adress", this.adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("google adress", this.google_adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("yandex adress", this.yandex_adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("osm adress", this.osm_adress, provider));
        resultContainer.getChildren().add(PropertyItemGenerator.generateTreeRedrawOnUnFocusPropertyControl("2gis adress", this.twoGis_adress, provider));
        return new NodeModelContainer(resultContainer, this, this);
    }


    @Override
    public NodeModelContainer getWorkbenchPropertyNode(ModelTreeProvider provider) {
        return null;
    }


    @Override
    public String getTreeItemName() {
        return getName();
    }


    public void getPropertiesFromNode(VBox vBox) {
        //по свойствам
        for (Node node : vBox.getChildren()) {
            VBox item = (VBox) node;
            //по контейнерам лейбл+текстбокс
            HBox hBox = (HBox) item.getChildren().get(0);
            for (Node subNode : hBox.getChildren()) {
                if (subNode.getClass() == TextField.class) {
                    TextField text = (TextField) subNode;
                    switch (text.getId()) {
                        case "name":
                            this.name = text.getText();
                            break;
                        case "adress":
                            this.adress = text.getText();
                            break;
                        case "google adress":
                            this.google_adress = text.getText();
                            break;
                        case "yandex adress":
                            this.yandex_adress = text.getText();
                            break;
                        case "osm adress":
                            this.osm_adress = text.getText();
                            break;
                        case "2gis adress":
                            this.twoGis_adress = text.getText();
                            break;
                    }
                    break;
                }
            }
        }

    }


    public int getIndexLevelWithName(String levelName) {
        int dontExist = -1;

        for (int i = 0; i < Levels.size(); i++)
            if (Levels.get(i).getName() == levelName)
                return i;

        return dontExist;
    }

    public String getLevelNameWithId(String id) {
        String name = "NaN";

        for (int i = 0; i < Levels.size(); i++) {
            if (Levels.get(i).getItemId().equals(id))
                return Levels.get(i).getName();
        }

        return name;
    }

    public WayPoint getWayPointWithId(String id) {
        String name = "NaN";

        for (int i = 0; i < Levels.size(); i++) {
            for (WayPoint wayPoint : Levels.get(i).getWayPointsToGraph())
                if (wayPoint.getItemId().equals(id))
                    return wayPoint;
        }

        return null;
    }

    public HashMap<String, String> getIdNameWaypoints() {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Level level : Levels)
            for (WayPoint wayPoint : level.getWayPointsToGraph())
                map.put(wayPoint.getItemId(), wayPoint.getName());
        return map;
    }

    @Override
    public String dataToString() {
        return "name = " + this.name + ", adress = " + this.adress;
    }

    @Override
    public void setPropertiesFromModalWindow(String key, Object value) {

    }

    @Override
    public Object getPropertyToModalWindow(String key) {
        return null;
    }

    @Override
    public NodeModelContainer getGraphPropertyNode() {
        return null;
    }

    @Override
    public ArrayList<Point2D> GetPointsToConnect() {
        return null;
    }

    @Override
    public Node GetDrowableElement() {
        Group parent = new Group();
        return parent;
    }

    public Node GetLevelsGraphNode() {
        Group parent = new Group();

        double angle;
        double levelsCount = this.Levels.size();
        int counter = 0;

        double shapeRadius = 30 * levelsCount;
        double circleRadius = 20;
        double centerX = 50 * levelsCount;
        double centerY = 50 * levelsCount;

        double levelX, levelY;

        ArrayList<Point2D> levelPoints = new ArrayList<>();
        HashMap<String, Level> levelMap = new HashMap<>();

        for (Level level : Levels) {
            levelMap.put(level.getItemId(), level);
            angle = counter * Math.PI / (levelsCount / 2);

            levelX = centerX + (shapeRadius * Math.sin(angle));
            levelY = centerY + (shapeRadius * Math.cos(angle));
            levelPoints.add(new Point2D.Double(levelX, levelY));

            level.setLevelShape(levelPoints.get(counter).getX(), levelPoints.get(counter).getY());
            parent.getChildren().add(level.GetDrowableElement());
            counter++;
        }

        counter = 0;
        String levelId;
        double x1, y1, x2, y2;
        double dist;
        double triangleArrowX1, triangleArrowX2, triangleArrowY1, triangleArrowY2;
        Point2D vector = new Point2D.Double();

        for (Level level : Levels) {
            for (WayPoint wayPoint : level.getWayPointsToGraph()) {
                if (wayPoint.getFinishWayPoint() != null) {
                    Line wayLine = new Line();
                    wayLine.setFill(Color.DARKGRAY);
                    wayLine.setStrokeWidth(2);

                    x1 = level.getLevelShape().getCenterX();
                    y1 = level.getLevelShape().getCenterY();
                    levelId = wayPoint.getFinishWayPoint().getFromLevelId();
                    Level levelBuffer = levelMap.get(levelId);
                    x2 = levelBuffer.getLevelShape().getCenterX();
                    y2 = levelBuffer.getLevelShape().getCenterY();

                    vector.setLocation(x2 - x1, y2 - y1);

                    dist = circleRadius / (Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2)));
                    vector.setLocation(vector.getX() * dist, vector.getY() * dist);

                    x1 = x1 + vector.getX();
                    y1 = y1 + vector.getY();

                    x2 = x2 - vector.getX();
                    y2 = y2 - vector.getY();

                    wayLine.setStartX(x1);
                    wayLine.setStartY(y1);
                    wayLine.setEndX(x2);
                    wayLine.setEndY(y2);

                    triangleArrowX1 = 7;
                    triangleArrowY1 = 25;

                    triangleArrowX2 = -7;
                    triangleArrowY2 = 25;

                    double rotateX = (x1 - x2) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                    double rotateY = (y1 - y2) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

                    Polygon triangle = new Polygon();
                    triangle.setFill(Color.BLACK);
                    triangle.getPoints().addAll(x2, y2,
                            x2 + (triangleArrowX1 * rotateY) + (triangleArrowY1 * rotateX), y2 + (-triangleArrowX1 * rotateX) + (triangleArrowY1 * rotateY),
                            x2 + (triangleArrowX2 * rotateY) + (triangleArrowY2 * rotateX), y2 + (-triangleArrowX2 * rotateX) + (triangleArrowY2 * rotateY));

                    parent.getChildren().add(wayLine);
                    parent.getChildren().add(triangle);
                }
            }
            counter++;
        }

        return parent;
    }

    public Node GetWaypointsGraphNode() {
        Group parent = new Group();

        double angle;
        int pointsCount;
        int counter = 0;
        double circleRadius = 20;


        double pointX, pointY;

        ArrayList<WayPoint> Points = new ArrayList<>();

        for (Level level : Levels)
            for (WayPoint wayPoint : level.getWayPointsToGraph())
                Points.add(wayPoint);

        pointsCount = Points.size();
        double shapeRadius = 30 * pointsCount;
        double centerX = 50 * pointsCount;
        double centerY = 50 * pointsCount;

        for (WayPoint point : Points) {
            angle = counter * Math.PI / ((double) pointsCount / 2);

            pointX = centerX + (shapeRadius * Math.sin(angle));
            pointY = centerY + (shapeRadius * Math.cos(angle));
//            levelPoints.add(new Point2D.Double(levelX, levelY));


            parent.getChildren().add(point.GetDrowableGraphElementAt(pointX, pointY));
            counter++;
        }


        counter = 0;
        String levelId;
        double x1, y1, x2, y2;
        double dist;
        double triangleArrowX1, triangleArrowX2, triangleArrowY1, triangleArrowY2;
        Point2D vector = new Point2D.Double();

        for (WayPoint point : Points) {
            if (point.getFinishWayPoint() != null) {
                Line wayLine = new Line();
                wayLine.setFill(Color.DARKGRAY);
                wayLine.setStrokeWidth(2);

                //point from
                x1 = point.getGraphPoint().getX();
                y1 = point.getGraphPoint().getY();

                //point to
                x2 = point.getFinishWayPoint().getGraphPoint().getX();
                y2 = point.getFinishWayPoint().getGraphPoint().getY();

                vector.setLocation(x2 - x1, y2 - y1);

                dist = circleRadius / (Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2)));
                vector.setLocation(vector.getX() * dist, vector.getY() * dist);

                x1 = x1 + vector.getX();
                y1 = y1 + vector.getY();

                x2 = x2 - vector.getX();
                y2 = y2 - vector.getY();

                wayLine.setStartX(x1);
                wayLine.setStartY(y1);
                wayLine.setEndX(x2);
                wayLine.setEndY(y2);

                triangleArrowX1 = 7;
                triangleArrowY1 = 25;

                triangleArrowX2 = -7;
                triangleArrowY2 = 25;

                double rotateX = (x1 - x2) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                double rotateY = (y1 - y2) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

                Polygon triangle = new Polygon();
                triangle.setFill(Color.BLACK);
                triangle.getPoints().addAll(x2, y2,
                        x2 + (triangleArrowX1 * rotateY) + (triangleArrowY1 * rotateX), y2 + (-triangleArrowX1 * rotateX) + (triangleArrowY1 * rotateY),
                        x2 + (triangleArrowX2 * rotateY) + (triangleArrowY2 * rotateX), y2 + (-triangleArrowX2 * rotateX) + (triangleArrowY2 * rotateY));

                parent.getChildren().add(wayLine);
                parent.getChildren().add(triangle);
            }

        }
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
        return "build";
    }

    public void addLevel(Level level) {
        this.Levels.add(level);
    }

    public void addLevel(String key) {
        this.Levels.add(new Level(key));
    }

    @Override
    public SortedMap<String, String> ModelErrorsCheck(SortedMap<String, String> messageMap) {

        //////////////////////////////errors
        if(this.name.isEmpty())
            messageMap.put("00001", "building name in empty or null");
        if(this.google_adress.isEmpty() && this.yandex_adress.isEmpty() && this.osm_adress.isEmpty() && this.twoGis_adress.isEmpty())
            messageMap.put("00002", "all address is empty");


        //////////////////////////////warnings
        if(!Levels.isEmpty())
            for(Level level : this.Levels)
                level.ModelErrorsCheck(messageMap);

        return messageMap;
    }


///////////////////////////////////////////////////////////////////


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGoogle_adress() {
        return google_adress;
    }

    public void setGoogle_adress(String google_adress) {
        this.google_adress = google_adress;
    }

    public String getYandex_adress() {
        return yandex_adress;
    }

    public void setYandex_adress(String yandex_adress) {
        this.yandex_adress = yandex_adress;
    }

    public String getOsm_adress() {
        return osm_adress;
    }

    public void setOsm_adress(String osm_adress) {
        this.osm_adress = osm_adress;
    }

    public ArrayList<Level> getLevels() {
        return Levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        Levels = levels;
    }

    public String getTwoGis_adress() {
        return twoGis_adress;
    }

    public void setTwoGis_adress(String twoGis_adress) {
        this.twoGis_adress = twoGis_adress;
    }



}
