package com.example.demofx.Utils.Dialogs;

import com.example.demofx.Models.Basic.HouseProject;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Enums.InTextTags;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class NewProjectDialog {

    private Dialog<String> window;

    private TextField textArea;
    private NodeModelContainer Container;

    public NewProjectDialog() {
                this.window = GetModalTextPropertyDialog();
    }

    public Dialog<String> getWindow() {
        return window;
    }

    public Optional<String> Show(){
        return window.showAndWait();
    }

    public Dialog<String> GetModalTextPropertyDialog() {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Create new project");

        BorderPane pane = new BorderPane();

        VBox verticalContainer = new VBox(11);



        //Input

        Label labelName = new Label("Project name:");

        this.textArea = new TextField();
        this.textArea.setId("textArea");
        this.textArea.setText(HouseProject.getInstance().getProjectName());
        VBox.setVgrow(this.textArea, Priority.ALWAYS);

        verticalContainer.getChildren().add(labelName);
        verticalContainer.getChildren().add(this.textArea);
        pane.setCenter(verticalContainer);


        dialog.setResizable(false);
        dialog.getDialogPane().setContent(pane);

        ButtonType buttonTypeOk = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);



        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                String str = this.textArea.getText();
                if(!str.isEmpty())
                    return str;
            }
            return null;
        });


        return dialog;
    }


}
