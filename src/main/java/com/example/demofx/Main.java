package com.example.demofx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

//        Parent root = loader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("project GEKATA");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setMaximized(true);

        SplitPane horisontalPane = (SplitPane) primaryStage.getScene().lookup("#MainHorisontalSplitPane");
        primaryStage.showingProperty().addListener(new ChangeListener<Boolean>() {
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
        primaryStage.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    verticalPane.setDividerPositions(0.1, 0.8);
                    observable.removeListener(this);
                }
            }
        });

        Controller c = loader.getController();
        c.EndWindowInitProcedure();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}


