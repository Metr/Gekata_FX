package com.example.demofx.Models.Basic;

import com.example.demofx.Controller;
import com.example.demofx.Interfaces.IGraphPrimitive;
import com.example.demofx.Main;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Events.EventContextController;
import com.example.demofx.Utils.Fabrics.ErrorCounterFabric;
import com.example.demofx.Utils.Fabrics.ItemIdFabric;
import com.example.demofx.Utils.Serializers.ProjectModel.FromJson.*;
import com.example.demofx.Utils.Serializers.ProjectModel.ToJson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class HouseProject {

    private static HouseProject houseProject;
    private Building building;

    private String ProjectName;
    private String ProjectPath;

    private final String FileFormat = ".bldproj";

    private NodeModelContainer SelectedItem;

    private boolean isNoErrors;

    public static synchronized HouseProject getInstance() {
        if (houseProject == null) {
            houseProject = new HouseProject();
            houseProject.building = new Building();
            houseProject.isNoErrors = true;
            houseProject.ProjectName = "Default";
            ItemIdFabric.resetCounter();

        }
        return houseProject;
    }


    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public NodeModelContainer getSelectedItem() {
        return SelectedItem;
    }

    public void setSelectedItem(NodeModelContainer selectedItem) {
        SelectedItem = selectedItem;
    }

    public String getProjectPath() {
        return ProjectPath;
    }

    public void setProjectPath(String projectPath) {
        ProjectPath = projectPath;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    ///MODEL LOGIC////////////////////////////////////////

    public void createCopyOfLevel(Level example){
        Level level = new Level("copy of " + example.getName());

        for (IGraphPrimitive wall : example.getWalls()) {
            BasicWall buffer = (BasicWall) wall;
            level.getWalls().add(new BasicWall(buffer.getStartX(), buffer.getStartY(), buffer.getEndX(), buffer.getEndY()));
        }

        for (IGraphPrimitive wall : example.getCurveWalls()) {
            QuadCurveWall buffer = (QuadCurveWall) wall;
            level.getCurveWalls().add(new QuadCurveWall(buffer.getStartX(), buffer.getStartY(), buffer.getControlX(), buffer.getControlY(), buffer.getEndX(), buffer.getEndY()));
        }

        this.building.getLevels().add(level);

        EventContextController.RenderAll();
    }


    ///PROJECT FILE////////////////////////////////////////

    public boolean RemoveObjectWithID(int removableID) {
        return building.removeObjectWithId(removableID);
    }


    public SortedMap<String, String> CheckErrors() {
        ErrorCounterFabric.resetCounter();

        SortedMap<String, String> report = new TreeMap<>();
        report = this.building.ModelErrorsCheck(report);

        for (String key : report.keySet()) {
            String value = report.get(key);
            System.out.println(key + ": " + value);
            if (key.substring(0, 1).equals("0"))
                this.isNoErrors = false;
        }
        return report;
    }

    public void SetProjectEmpty(String projectName){
        houseProject.building = new Building();
        houseProject.isNoErrors = true;
        houseProject.ProjectName = projectName;
        houseProject.SelectedItem = null;

        EventContextController.getWorkbenchProvider().Clear();
        EventContextController.getModelTreeProvider().PropertyItemClear();

        ItemIdFabric.resetCounter();
    }

    public void saveProject() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(HouseProject.class, new ProjectSerializer())
                .registerTypeAdapter(Building.class, new BuildingSerializer())
                .registerTypeAdapter(Level.class, new LevelSerializer())
                .registerTypeAdapter(BasicWall.class, new WallSerializer())
                .registerTypeAdapter(QuadCurveWall.class, new CurveSerializer())
                .registerTypeAdapter(InterestPoint.class, new InterestPointSerializer())
                .registerTypeAdapter(WayPoint.class, new WayPointSerializer())
                .setPrettyPrinting()
                .create();

        if (this.ProjectPath != null && !this.ProjectPath.isEmpty() ) {
            String path = this.ProjectPath;
            String json = gson.toJson(this);
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                out.write(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            saveProjectAs();
        }
    }

    public void saveProjectAs() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(HouseProject.class, new ProjectSerializer())
                .registerTypeAdapter(Building.class, new BuildingSerializer())
                .registerTypeAdapter(Level.class, new LevelSerializer())
                .registerTypeAdapter(BasicWall.class, new WallSerializer())
                .registerTypeAdapter(QuadCurveWall.class, new CurveSerializer())
                .registerTypeAdapter(InterestPoint.class, new InterestPointSerializer())
                .registerTypeAdapter(WayPoint.class, new WayPointSerializer())
                .setPrettyPrinting()
                .create();


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Source Files", FileFormat));


        Stage stage = new Stage();
        File savedFile = fileChooser.showSaveDialog(stage);
        if (savedFile != null) {
            String path = savedFile.getPath();
            this.ProjectName = savedFile.getName() + FileFormat;
            this.ProjectPath = savedFile.getAbsolutePath() + FileFormat;
            String json = gson.toJson(this);
            try (PrintWriter out = new PrintWriter(new FileWriter(path + FileFormat))) {
                out.write(json);
//                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void loadProject() {

    }

    public void openProject() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(HouseProject.class, new ProjectSerializer())
                .registerTypeAdapter(Building.class, new BuildingSerializer())
                .registerTypeAdapter(Level.class, new LevelSerializer())
                .registerTypeAdapter(BasicWall.class, new WallSerializer())
                .registerTypeAdapter(QuadCurveWall.class, new CurveSerializer())
                .registerTypeAdapter(InterestPoint.class, new InterestPointSerializer())
                .registerTypeAdapter(WayPoint.class, new WayPointSerializer())

                .registerTypeAdapter(HouseProject.class, new ProjectDeserializer())
                .registerTypeAdapter(Building.class, new BuildingDeserializer())
                .registerTypeAdapter(Level.class, new LevelDeserializer())
                .registerTypeAdapter(BasicWall.class, new WallDeserializer())
                .registerTypeAdapter(QuadCurveWall.class, new CurveDeserializer())
                .registerTypeAdapter(InterestPoint.class, new InterestPointDeserializer())
                .registerTypeAdapter(WayPoint.class, new WayPointDeserializer())

                .setPrettyPrinting()
                .create();


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Source Files", FileFormat));


        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (JsonReader reader = new JsonReader(new FileReader(file.getAbsoluteFile()))) {
                gson.fromJson(reader, HouseProject.class);
                Main.getpStage().setTitle("project GEKATA: " + HouseProject.getInstance().getProjectName());
                EventContextController.RenderAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


