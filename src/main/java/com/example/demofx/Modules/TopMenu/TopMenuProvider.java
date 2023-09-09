package com.example.demofx.Modules.TopMenu;

import com.example.demofx.Models.Basic.HouseProject;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Dialogs.NewProjectDialog;
import com.example.demofx.Utils.Dialogs.TextPropertyDialog;
import com.example.demofx.Utils.Dialogs.WorkbenchPropertyDialog;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Events.EventContextController;
import javafx.scene.control.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

public class TopMenuProvider implements PropertyChangeListener {

    HouseProject houseProject;

    private MenuBar menuBar;

    private PropertyChangeSupport support;

    public TopMenuProvider(MenuBar topMenuBar) {
        houseProject = HouseProject.getInstance();
        this.menuBar = topMenuBar;
        support = new PropertyChangeSupport(this);
        TopMenuProviderInitialize();
    }

    private void TopMenuProviderInitialize() {
        menuBar.getMenus().clear();
        //project, settings

        Menu menuProject = new Menu("Project..");
        //create/open/upload/save/save_as/project_check

        MenuItem createNewProjectItem = new MenuItem("Create Project");
        createNewProjectItem.setOnAction(event -> createEmptyProject());

        MenuItem openProjectItem = new MenuItem("Open Project");
        openProjectItem.setOnAction(event -> openProject());

        MenuItem uploadProjectItem = new MenuItem("connect to...");
        uploadProjectItem.setOnAction(event -> drawLevelsGraph());

        MenuItem saveProjectItem = new MenuItem("Save Project");
        saveProjectItem.setOnAction(event -> saveProject());

        MenuItem saveAsProjectItem = new MenuItem("Save Project As..");
        saveAsProjectItem.setOnAction(event -> saveProjectAs());

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        MenuItem verifyProjectItem = new MenuItem("Check errors");
        verifyProjectItem.setOnAction(event -> verifyProject());

        menuProject.getItems().add(createNewProjectItem);
        menuProject.getItems().add(openProjectItem);
        menuProject.getItems().add(uploadProjectItem);
        menuProject.getItems().add(saveProjectItem);
        menuProject.getItems().add(saveAsProjectItem);
        menuProject.getItems().add(separatorMenuItem);
        menuProject.getItems().add(verifyProjectItem);

        ///VIEW/////////

        Menu menuView = new Menu("View");

        MenuItem levelGraphView = new MenuItem("Get levels Graph");
        levelGraphView.setOnAction(event -> drawLevelsGraph());

        MenuItem WayPointsGraphView = new MenuItem("Get waypoints Graph");
        WayPointsGraphView.setOnAction(event -> drawWayPointsGraph(null));


        ///SETTINGS////

        Menu menuSettings = new Menu("Settings");

        MenuItem viewSettings = new MenuItem("View Settings");
        viewSettings.setOnAction(event -> openWorkbenchPropertyWindow());


        MenuItem networkSettings = new MenuItem("Net Settings");


        ///FINAL////////


        menuSettings.getItems().add(viewSettings);
        menuSettings.getItems().add(networkSettings);

        menuView.getItems().add(levelGraphView);
        menuView.getItems().add(WayPointsGraphView);

        menuBar.getMenus().add(menuProject);
        menuBar.getMenus().add(menuSettings);
        menuBar.getMenus().add(menuView);
    }

    private void AddTopLevelMenuItem(String text) {

    }

    private void addChildLevelMenuItem(String text) {

    }


    ////////////////////////////


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void BroadcastReRenderCommand() {
        support.firePropertyChange("reRender", null, null);
    }

    public void BroadcastDrawLevelGraphCommand() {
        support.firePropertyChange("drawLevelGraph", null, null);
    }

    public void BroadcastDrawWayPointsGraphCommand(NodeModelContainer container) {

        support.firePropertyChange("drawWayPointsGraph", null, container);
    }

    //////////ITEMS_LOGIC////////////////////////



    //////////////////////////////PROJECT_LOGIC

    private void verifyProject() {
        EventContextController.getBottomOutputProvider().setText(HouseProject.getInstance().CheckErrors());
        //TODO report log output
    }

    private void createEmptyProject(){
        NewProjectDialog newProjectDialog = new NewProjectDialog();
        Optional<String> result = newProjectDialog.Show();

        if (result.isPresent()) {
            HouseProject.getInstance().SetProjectEmpty(result.get());
            EventContextController.RenderAll();
        }
    }

    private void saveProject(){
        HouseProject.getInstance().saveProject();
    }

    private void saveProjectAs(){
        HouseProject.getInstance().saveProjectAs();
    }

    private void openProject(){
        HouseProject.getInstance().openProject();
    }

    //////////////////////////////SETTINGS_LOGIC
    private void openWorkbenchPropertyWindow() {
        WorkbenchPropertyDialog dialog = new WorkbenchPropertyDialog();
        dialog.Show();
        BroadcastReRenderCommand();
    }


    ///////////////////////////////VIEW_LOGIC
    private void drawLevelsGraph() {
        BroadcastDrawLevelGraphCommand();
    }

    private void drawWayPointsGraph(NodeModelContainer container) {
        BroadcastDrawWayPointsGraphCommand(container);
    }

}


