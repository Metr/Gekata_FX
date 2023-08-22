package com.example.demofx.Utils.Dialogs;

import com.example.demofx.Utils.Configs.WorkbenchProperties;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Enums.InTextTags;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class WorkbenchPropertyDialog {

    private Dialog<String> window;

    private CheckBox radiusVisibilityBox;

    private CheckBox namesVisibilityBox;

    private CheckBox controlDotsConnectedBox;

    private TextField dotsConnectingRadiusField;

    private TextField BGOpacityField;

    public WorkbenchPropertyDialog() {
        this.window = GetModalWorkbenchPropertyDialog();
    }

    public Optional<String> Show() {
        return window.showAndWait();
    }


    public Dialog<String> GetModalWorkbenchPropertyDialog() {
        String str = "";
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Workbench Properties");

        BorderPane pane = new BorderPane();
        VBox verticalContainer = new VBox(11);


        //Input

        // is radius draw

        HBox RadiusDrawContainer = new HBox(2);

        Label labelRadius = new Label("Drawing all Point radius's");
        RadiusDrawContainer.getChildren().add(labelRadius);

        radiusVisibilityBox = new CheckBox();
        radiusVisibilityBox.setSelected((boolean)WorkbenchProperties.getInstance().getPropertyByName("isAllRadiusDraw"));
        RadiusDrawContainer.getChildren().add(radiusVisibilityBox);
        verticalContainer.getChildren().add(RadiusDrawContainer);

        //is dot names draw

        HBox NameDrawContainer = new HBox(2);

        Label nameRadius = new Label("Drawing Points \"name\" property");
        NameDrawContainer.getChildren().add(nameRadius);

        namesVisibilityBox = new CheckBox();
        namesVisibilityBox.setSelected((boolean)WorkbenchProperties.getInstance().getPropertyByName("isWayPointsNamed"));
        NameDrawContainer.getChildren().add(namesVisibilityBox);
        verticalContainer.getChildren().add(NameDrawContainer);

        //control dots connecting

        HBox ConnectingDotsContainer = new HBox(2);
        Label labelDots = new Label("Control dots are connecting");
        ConnectingDotsContainer.getChildren().add(labelDots);

        controlDotsConnectedBox = new CheckBox();
        controlDotsConnectedBox.setSelected((boolean)WorkbenchProperties.getInstance().getPropertyByName("isCDConnecting"));
        controlDotsConnectedBox.setOnAction(event -> {
            dotsConnectingRadiusField.setDisable(!dotsConnectingRadiusField.isDisabled());
        });
        ConnectingDotsContainer.getChildren().add(controlDotsConnectedBox);
        verticalContainer.getChildren().add(ConnectingDotsContainer);

        HBox ConnectingDotsRadiusContainer = new HBox(2);
        Label labelRadiusDots = new Label("Control dots connecting radius (int)");
        ConnectingDotsRadiusContainer.getChildren().add(labelRadiusDots);

        dotsConnectingRadiusField = new TextField();
        dotsConnectingRadiusField.setText("" + (int)WorkbenchProperties.getInstance().getPropertyByName("CDConnectRadius"));
        dotsConnectingRadiusField.setDisable(!(boolean)WorkbenchProperties.getInstance().getPropertyByName("isCDConnecting"));
        ConnectingDotsRadiusContainer.getChildren().add(dotsConnectingRadiusField);
        verticalContainer.getChildren().add(ConnectingDotsRadiusContainer);

        //BackGround opacity
        HBox BGOpacityContainer = new HBox(2);
        Label BGOpacity = new Label("Background level opacity % [0;100]");
        BGOpacityContainer.getChildren().add(BGOpacity);

        BGOpacityField = new TextField();
        BGOpacityField.setText("" + (int) WorkbenchProperties.getInstance().getPropertyByName("BGOpacity"));
        BGOpacityContainer.getChildren().add(BGOpacityField);
        verticalContainer.getChildren().add(BGOpacityContainer);

        //Final

        pane.setCenter(verticalContainer);

        dialog.setResizable(false);
        dialog.getDialogPane().setContent(pane);

        ButtonType buttonTypeOk = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);


        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                if (trySetWorkbenchProperties())
                    return str;
                else return null;
            }
            return null;
        });
        return dialog;
    }

    private boolean trySetWorkbenchProperties() {
        try {
            WorkbenchProperties.getInstance().changeProperty("isAllRadiusDraw", this.radiusVisibilityBox.isSelected());
            WorkbenchProperties.getInstance().changeProperty("isCDConnecting", this.controlDotsConnectedBox.isSelected());
            WorkbenchProperties.getInstance().changeProperty("CDConnectRadius",Integer.parseInt(this.dotsConnectingRadiusField.getText()));
            WorkbenchProperties.getInstance().changeProperty("isWayPointsNamed", this.namesVisibilityBox.isSelected());
            WorkbenchProperties.getInstance().changeProperty("BGOpacity", Integer.parseInt(this.BGOpacityField.getText()));

            return true;
        } catch (Exception ex) {

            return false;
        }
    }


}
