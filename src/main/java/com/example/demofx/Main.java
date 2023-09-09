package com.example.demofx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;


public class Main extends Application {

    private static Stage pStage;

    private static Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        pStage = primaryStage;

        pStage.setTitle("project GEKATA");
        pStage.setScene(new Scene(root, 300, 275));
        pStage.setMaximized(true);

        SplitPane horisontalPane = (SplitPane) pStage.getScene().lookup("#MainHorisontalSplitPane");
        pStage.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    horisontalPane.setDividerPositions(0.9);
                    observable.removeListener(this);
                }
            }
        });

        var coll = horisontalPane.getItems();
        SplitPane verticalPane = (SplitPane) coll.get(0).lookup("#MainVerticalSplitPane");
        pStage.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    verticalPane.setDividerPositions(0.1, 0.8);
                    observable.removeListener(this);
                }
            }
        });

        controller = loader.getController();
        controller.EndWindowInitProcedure();
        pStage.show();

    }

    public static Stage getpStage() {
        return pStage;
    }

    public static Controller getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }




}


