package com.example.demofx.Utils.Generators;

import com.example.demofx.Models.HouseProject;
import com.example.demofx.Models.Level;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Events.EventContextController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public class PropertyItemGenerator {

    public static VBox generateTreeRedrawOnChangePropertyControl(String label, String startValue, ModelTreeProvider provider) {
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);
        VBox.setVgrow(propertyItem, Priority.ALWAYS);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);

        TextField f = new TextField(startValue);
        VBox.setVgrow(f, Priority.ALWAYS);
//        f.setAlignment(Pos.CENTER_LEFT);
        f.setId(label);

        f.setOnKeyReleased(event -> {
            provider.onTreeItemChange();
            provider.BroadcastReRenderCommand();
        });

        propertyItem.getChildren().add(f);

        VBox.setVgrow(resultContainer, Priority.ALWAYS);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }

    public static VBox generateTreeRedrawOnUnFocusPropertyControl(String label, String startValue, ModelTreeProvider provider) {
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem;
        propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);

        TextField f = new TextField(startValue);
//        TextField f = new TextField(startValue) {
//          @Override
//          public void paste() {
//              super.paste();
//              provider.onTreeItemChange();
//              provider.BroadcastReRenderCommand();
//          }
//        };

        VBox.setVgrow(f, Priority.ALWAYS);
        f.setId(label);


        f.focusedProperty().addListener(event -> {
            if(!f.isFocused()) {
                provider.onTreeItemChange();
                provider.BroadcastReRenderCommand();
            }
        });

        f.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                provider.onTreeItemChange();
                EventContextController.RenderAll();
            }
        } );

        VBox.setVgrow(propertyItem, Priority.ALWAYS);
        VBox.setVgrow(resultContainer, Priority.ALWAYS);

        propertyItem.getChildren().add(f);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }

    public static VBox generateTreePropertyListControl(String label, String item, HashMap<String, String> items, ModelTreeProvider provider) {
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem;
        propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);

        ObservableList<String> levels = FXCollections.observableArrayList(items.values());
        ChoiceBox<String> choiseBox = new ChoiceBox<String>(levels);
        choiseBox.setValue(item);
        choiseBox.setMaxWidth(200);
        choiseBox.setMinWidth(200);
        choiseBox.setId(label);
        choiseBox.setOnAction(event -> {
            provider.onTreeItemSelectionChange();
            EventContextController.RenderAll();
        });

        propertyItem.getChildren().add(choiseBox);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }

    public static VBox generateWorkbenchPropertyControl(String label, String startValue, ModelTreeProvider provider) {
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem;
        propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);

        TextField f = new TextField(startValue);
        f.setMaxWidth(200);
        f.setMinWidth(200);
        f.setAlignment(Pos.CENTER_LEFT);
        f.setId(label);
        f.setOnKeyReleased(event ->  provider.onWorkbenchItemChange() );


        propertyItem.getChildren().add(f);
        resultContainer.getChildren().add(propertyItem);
        return resultContainer;
    }

    public static VBox generateModalWindowControl(String label, String startValue, ModelTreeProvider provider){
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem;
        propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);

        Label l = new Label(label);
        l.setMaxWidth(100);
        l.setMinWidth(100);
        l.setAlignment(Pos.CENTER_LEFT);
        propertyItem.getChildren().add(l);

        Button button = new Button("Change");
        button.setOnAction(event -> {
            ActionEvent e = new ActionEvent();
            provider.onOpenModalWindowClick(e);
        });
        propertyItem.getChildren().add(button);



        TextArea area = new TextArea(startValue);
        area.setEditable(false);
        area.setPrefWidth(300);
        area.setWrapText(true);

        area.setId(label);
        VBox.setVgrow(area, Priority.ALWAYS);
        area.setOnKeyReleased(event ->  provider.onWorkbenchItemChange() );

        VBox.setVgrow(resultContainer, Priority.ALWAYS);
        VBox.setVgrow(propertyItem, Priority.ALWAYS);


        resultContainer.getChildren().add(propertyItem);
        resultContainer.getChildren().add(area);
        return resultContainer;
    }


}
