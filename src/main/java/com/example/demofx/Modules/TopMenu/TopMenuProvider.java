package com.example.demofx.Modules.TopMenu;

import com.example.demofx.Models.HouseProject;
import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Dialogs.WorkbenchPropertyDialog;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.StandardOpenOption;

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

        //menuProject.getItems().add()

        ///SETTINGS////

        Menu menuSettings = new Menu("Settings");

        MenuItem viewSettings = new MenuItem("View Settings");
        viewSettings.setOnAction(event -> openWorkbenchPropertyWindow());





        MenuItem networkSettings = new MenuItem("Net Settings");



        ///FINAL////////



        menuSettings.getItems().add(viewSettings);
        menuSettings.getItems().add(networkSettings);

        menuBar.getMenus().add(menuProject);
        menuBar.getMenus().add(menuSettings);
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


    //////////ITEMS_LOGIC////////////////////////

    public void openWorkbenchPropertyWindow(){
        WorkbenchPropertyDialog dialog = new WorkbenchPropertyDialog();
        dialog.Show();
        BroadcastReRenderCommand();
    }

}


