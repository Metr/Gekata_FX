package com.example.demofx.Utils.Dialogs;

import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Enums.InTextTags;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.util.Optional;

public class TextPropertyDialog {

    private Dialog<NodeModelContainer> window;

    private TextArea textArea;
    private NodeModelContainer Container;

    public TextPropertyDialog(DescriptionTypes type, NodeModelContainer container, String key) {
        switch (type) {
            case TEXT_DESCR:
                this.window = GetModalTextPropertyDialog(container, key);
                break;
            case GRID_DESCR:
                this.window = null;
                break;
        }
    }

    public Dialog<NodeModelContainer> getWindow() {
        return window;
    }

    public Optional<NodeModelContainer> Show(){
        return window.showAndWait();
    }

    public Dialog<NodeModelContainer> GetModalTextPropertyDialog(NodeModelContainer container, String key) {
        this.Container = container;


        Dialog<NodeModelContainer> dialog = new Dialog<>();
        dialog.setTitle("Change property: " + key);

        BorderPane pane = new BorderPane();

        VBox verticalContainer = new VBox(11);

        HBox HButtonsContainer = new HBox(2);


        Button headlineButton = new Button("H1");
        headlineButton.setMaxHeight(20);
        headlineButton.setOnAction(event -> highlightSelected(InTextTags.HEADER));
        HButtonsContainer.getChildren().add(headlineButton);

        Button subHeadlineButton = new Button("H2");
        subHeadlineButton.setMaxHeight(20);
        subHeadlineButton.setOnAction(event -> highlightSelected(InTextTags.SUBHEADER));
        HButtonsContainer.getChildren().add(subHeadlineButton);

        Button BoltButton = new Button("Bolt");
        BoltButton.setMaxHeight(20);
        BoltButton.setOnAction(event -> highlightSelected(InTextTags.BOLT));
        HButtonsContainer.getChildren().add(BoltButton);

        Button cursiveButton = new Button("Cursive");
        cursiveButton.setMaxHeight(20);
        cursiveButton.setOnAction(event -> highlightSelected(InTextTags.CURSIVE));
        HButtonsContainer.getChildren().add(cursiveButton);

        Button underlineButton = new Button("Underline");
        underlineButton.setMaxHeight(20);
        underlineButton.setOnAction(event -> highlightSelected(InTextTags.UNDERLINE));
        HButtonsContainer.getChildren().add(underlineButton);

        //Input

        this.textArea = new TextArea();
        this.textArea.setId("textArea");
        this.textArea.setWrapText(true);
        this.textArea.setText((String)container.getPropertyModel().getPropertyToModalWindow("descr"));
        VBox.setVgrow(this.textArea, Priority.ALWAYS);

        //HButtonsContainer.setStyle("-fx-background-color: #77aa22");

        verticalContainer.getChildren().add(HButtonsContainer);
        verticalContainer.getChildren().add(this.textArea);
        //verticalContainer.setStyle("-fx-background-color: #77cccc");

        //pane.setStyle("-fx-background-color: #7777cc");
        pane.setCenter(verticalContainer);


        dialog.setResizable(true);
        dialog.getDialogPane().setContent(pane);

        ButtonType buttonTypeOk = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk) {
                Container.getPropertyModel().setPropertiesFromModalWindow("descr", textArea.getText());
                return Container;
            }
            return null;
        });


        return dialog;
    }

    private void highlightSelected(InTextTags tag){
        IndexRange indexRange = textArea.getSelection();
        String taggedText = textArea.getSelectedText();

        if(!textArea.getSelectedText().isEmpty() )
        {
            switch (tag){
                case HEADER -> {
                    taggedText = "<H1>" + taggedText + "</H1>";
                }
                case SUBHEADER -> {
                    taggedText = "<H2>" + taggedText + "</H2>";
                }
                case BOLT -> {
                    taggedText = "<Bolt>" + taggedText + "</Bolt>";
                }
                case CURSIVE -> {
                    taggedText = "<Cursive>" + taggedText + "</Cursive>";
                }
                case UNDERLINE -> {
                    taggedText = "<Underline>" + taggedText + "</Underline>";
                }
            }
            StringBuilder textSb = new StringBuilder(textArea.getText());
            textSb.replace(indexRange.getStart(), indexRange.getEnd(), taggedText);
            textArea.setText(textSb.toString());
        }
    }


}
