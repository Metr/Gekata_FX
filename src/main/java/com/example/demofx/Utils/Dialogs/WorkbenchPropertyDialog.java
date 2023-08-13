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

    private CheckBox controlDotsConnectedBox;

    private TextField dotsConnectingRadiusField;


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
        radiusVisibilityBox.setSelected(WorkbenchProperties.getInstance().isAllRadiusDrawing());
        RadiusDrawContainer.getChildren().add(radiusVisibilityBox);
        verticalContainer.getChildren().add(RadiusDrawContainer);


        //control dots connecting

        HBox ConnectingDotsContainer = new HBox(2);
        Label labelDots = new Label("Control dots are connecting");
        ConnectingDotsContainer.getChildren().add(labelDots);

        controlDotsConnectedBox = new CheckBox();
        controlDotsConnectedBox.setSelected(WorkbenchProperties.getInstance().isControlDotsConnecting());
        controlDotsConnectedBox.setOnAction(event -> {
            dotsConnectingRadiusField.setDisable(!dotsConnectingRadiusField.isDisabled());
        });
        ConnectingDotsContainer.getChildren().add(controlDotsConnectedBox);
        verticalContainer.getChildren().add(ConnectingDotsContainer);

        HBox ConnectingDotsRadiusContainer = new HBox(2);
        Label labelRadiusDots = new Label("Control dots connecting radius (int)");
        ConnectingDotsRadiusContainer.getChildren().add(labelRadiusDots);

        dotsConnectingRadiusField = new TextField();
        dotsConnectingRadiusField.setText("" + WorkbenchProperties.getInstance().getControlDotsConnectingRadius());
        dotsConnectingRadiusField.setDisable(!WorkbenchProperties.getInstance().isControlDotsConnecting());
        ConnectingDotsRadiusContainer.getChildren().add(dotsConnectingRadiusField);
        verticalContainer.getChildren().add(ConnectingDotsRadiusContainer);

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
            WorkbenchProperties.getInstance().setAllRadiusDrawing(this.radiusVisibilityBox.isSelected());
            WorkbenchProperties.getInstance().setControlDotsConnecting(this.controlDotsConnectedBox.isSelected());
            WorkbenchProperties.getInstance().setControlDotsConnectingRadius(Integer.parseInt(this.dotsConnectingRadiusField.getText()));
            return true;
        } catch (Exception ex) {

            return false;
        }
    }


}
