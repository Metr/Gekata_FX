package com.example.demofx.Utils.Generators;

import com.example.demofx.Models.Basic.HouseProject;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Events.EventContextController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class PropertyItemGenerator {

    public static VBox generateTreeButton(String label, EventHandler handler){
        VBox resultContainer = new VBox();

        resultContainer.setSpacing(5);
        resultContainer.setAlignment(Pos.CENTER_LEFT);
        resultContainer.setFillWidth(true);

        HBox propertyItem = new HBox();
        propertyItem.setAlignment(Pos.CENTER_LEFT);
        propertyItem.setSpacing(5);
        VBox.setVgrow(propertyItem, Priority.ALWAYS);

        Button deleteButton = new Button(label);
        deleteButton.setOnAction(handler);

        propertyItem.getChildren().add(deleteButton);

        VBox.setVgrow(resultContainer, Priority.ALWAYS);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }


    //label + text field
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
            int pointer = f.getCaretPosition();
            provider.onTreeItemChange();
            provider.BroadcastReRenderCommand();
            f.positionCaret(pointer);
        });

        propertyItem.getChildren().add(f);

        VBox.setVgrow(resultContainer, Priority.ALWAYS);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }

    //label + text field
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

    //label + listBox
    public static VBox generateTreePropertyListControl(String label, String item, HashMap<Integer, String> items, ModelTreeProvider provider) {
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
        choiseBox.setId(label);
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

    public static VBox generateTreeDescrTypesControl(String label, String item, ModelTreeProvider provider) {
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

        ObservableList<String> items = FXCollections.observableArrayList();

        items.add(DescriptionTypes.TEXT_DESCR.name());
        items.add(DescriptionTypes.GRID_DESCR.name());
        items.add(DescriptionTypes.CHAT_DESCR.name());

        ChoiceBox<String> choiseBox = new ChoiceBox<String>(items);
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

    //GRAPH ITEMS////////////////////////////

    public static VBox generateGraphRedrawOnChangePropertyControl(String label, String startValue, ModelTreeProvider provider) {
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
            int pointer = f.getCaretPosition();
            provider.onTreeItemChange();
            EventContextController.getTopMenuProvider().BroadcastDrawWayPointsGraphCommand(null);
            f.positionCaret(pointer);
        });

        propertyItem.getChildren().add(f);

        VBox.setVgrow(resultContainer, Priority.ALWAYS);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }

    public static VBox generateGraphPropertyListControl(String label, String item, HashMap<Integer, String> items, ModelTreeProvider provider) {
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
            EventContextController.getTopMenuProvider().BroadcastDrawWayPointsGraphCommand(null);
        });

        propertyItem.getChildren().add(choiseBox);
        resultContainer.getChildren().add(propertyItem);

        return resultContainer;
    }


}
