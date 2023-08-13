package com.example.demofx;

import com.example.demofx.Models.HouseProject;
import com.example.demofx.Modules.ItemList.ItemListProvider;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Modules.TopMenu.TopMenuProvider;
import com.example.demofx.Modules.Workbench.WorkbenchProvider;
import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Events.EventContextController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class Controller {

    @FXML
    public GridPane MainContainer;

    @FXML
    public TreeView ModelTreeControl;

    @FXML
    public VBox ItemPropertyControl;

    @FXML
    public VBox LevelItemList;

    @FXML
    public ScrollPane ScrollGraphLevelList;

    @FXML
    private Pane MainCanvas;

    @FXML
    private SplitPane MainHorisontalSplitPane;

    @FXML
    private AnchorPane item_1;

    @FXML
    private SplitPane MainVerticalSplitPane;

    @FXML
    private AnchorPane item_2;

    @FXML
    private VBox GraphElementsControl;

    @FXML
    private ScrollPane ScrollGraphItemList;

    @FXML
    private Button button1;

    @FXML
    private MenuBar TopMenuBar;

    WorkbenchProvider workbenchProvider;
    ItemListProvider itemListProvider;
    ModelTreeProvider modelTreeProvider;

    TopMenuProvider topMenuProvider;

    public Controller() {
        System.out.println("start");
    }

    @FXML
    private void click(ActionEvent event) {


        System.out.println("test click " + HouseProject.getInstance().getClass());
    }


    public void EndWindowInitProcedure() {

        WorkbenchProperties w = WorkbenchProperties.getInstance();

        workbenchProvider = new WorkbenchProvider(MainCanvas);
        itemListProvider = new ItemListProvider(GraphElementsControl, ScrollGraphItemList, LevelItemList, ScrollGraphLevelList);
        modelTreeProvider = new ModelTreeProvider(ModelTreeControl, ItemPropertyControl);
        topMenuProvider = new TopMenuProvider(TopMenuBar);

        EventContextController contextController = new EventContextController();
        contextController.setProviders(workbenchProvider, itemListProvider, modelTreeProvider, topMenuProvider);

    }

    //TODO local
    //Header menu
    //  -Radius visibility
    //  -Control dots trust-radius connect
    //  -create/save/select file project
    //      -transport model
    //      -
    //  -level graph analyse
    //Upload image to level
    //

    //TODO server's up
    //download project window


}

