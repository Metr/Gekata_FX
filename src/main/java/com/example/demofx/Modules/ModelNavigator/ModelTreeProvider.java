package com.example.demofx.Modules.ModelNavigator;

import com.example.demofx.Interfaces.IPropertyChangeble;
import com.example.demofx.Models.Basic.*;
import com.example.demofx.Utils.Containers.NodeModelContainer;
import com.example.demofx.Utils.Dialogs.TextPropertyDialog;
import com.example.demofx.Utils.Enums.DescriptionTypes;
import com.example.demofx.Utils.Events.EventContextController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public class ModelTreeProvider implements PropertyChangeListener {

    private TreeView TreeView;
    private VBox PropertyView;

    private IPropertyChangeble ChangeableItem;
    private PropertyChangeSupport support;

    private boolean isObjectChanged = false;

    public ModelTreeProvider(TreeView treeView, VBox propertyView) {
        this.TreeView = treeView;
        this.PropertyView = propertyView;
        support = new PropertyChangeSupport(this);
        modelTreeInitialize();
    }

    private void modelTreeInitialize() {
        TreeItemContainer<String> root = new TreeItemContainer<String>
                (HouseProject.getInstance().getBuilding().getName(), HouseProject.getInstance().getBuilding());


        // по этажам
        //for (Map.Entry lev : HouseProject.getInstance().getBuilding().getLevels().entrySet()) {
        for (Level level : HouseProject.getInstance().getBuilding().getLevels()) {
            TreeItemContainer<String> tmpLevelItem = new TreeItemContainer<String>(level.getName(), level);

            // по содержимому этажей - вейпоинтам
            for (int i = 0; i < level.getWayPoints().size(); i++) {
                WayPoint wayPoint = (WayPoint) level.getWayPoints().get(i);
                tmpLevelItem.getChildren().add(new TreeItemContainer<String>(wayPoint.getTreeItemName(), wayPoint));
            }

            for (int i = 0; i < level.getInterestPoints().size(); i++) {
                InterestPoint interest = (InterestPoint) level.getInterestPoints().get(i);
                tmpLevelItem.getChildren().add(new TreeItemContainer<String>(interest.getTreeItemName(), interest));
            }

            // по содержимому этажей - стенам
            for (int i = 0; i < level.getWalls().size(); i++) {
                BasicWall wall = (BasicWall) level.getWalls().get(i);
                tmpLevelItem.getChildren().add(new TreeItemContainer<String>(wall.getTreeItemName(), wall));
            }

            for (int i = 0; i < level.getCurveWalls().size(); i++) {
                QuadCurveWall curveWall = (QuadCurveWall) level.getCurveWalls().get(i);
                tmpLevelItem.getChildren().add(new TreeItemContainer<String>(curveWall.getTreeItemName(), curveWall));
            }

            //установили результат
            root.getChildren().add(tmpLevelItem);
        }
        TreeView.setRoot(root);


        MultipleSelectionModel<TreeItem<String>> selectionModel = TreeView.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        selectionModel.selectedItemProperty().addListener(
                (changed, oldValue, newValue) -> this.TreeViewSelectionChanged(changed, oldValue, newValue)
        );
    }

    public void PropertyItemClear(){
        this.PropertyView.getChildren().clear();
    }

    public void TreeViewSelectionChanged(ObservableValue<? extends TreeItem<String>> changed, TreeItem<String> oldValue, TreeItem<String> newValue) {
        if (newValue != null) {
            SelectionModel<TreeItem<String>> selectionModel = TreeView.getSelectionModel();
            TreeItemContainer<IPropertyChangeble> newSelectedItem = (TreeItemContainer<IPropertyChangeble>) selectionModel.getSelectedItem();

            HouseProject.getInstance().setSelectedItem(newSelectedItem.getModel().getTreePropertyNode(this));

            PropertyView.getChildren().clear();
            PropertyView.getChildren().add(HouseProject.getInstance().getSelectedItem().getPropertyNode());
            if (oldValue == null)
                oldValue = newValue;
            if (isObjectChanged) {
                //oldValue.setValue(ChangeableItem.getTreeItemName());
                BroadcastReRenderCommand();
                isObjectChanged = false;
            }
        }
    }

    public void onTreeItemChange() {
        try {
            HouseProject.getInstance().getSelectedItem().
                    getPropertyModel().getPropertiesFromNode((VBox)this.PropertyView.getChildren().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //изменение выбора листбокса
    public void onTreeItemSelectionChange(){
        try {
            HouseProject.getInstance().getSelectedItem().
                    getPropertyModel().getPropertiesFromNode((VBox)this.PropertyView.getChildren().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onModalWindowItemChange(){
        try {
            HouseProject.getInstance().getSelectedItem().
                    getPropertyModel().getPropertiesFromNode((VBox)this.PropertyView.getChildren().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onWorkbenchItemChange(){
        System.out.println("wwwww");
        HouseProject.getInstance().getSelectedItem().getPropertyModel().
                getPropertiesFromNode((VBox) PropertyView.getChildren().get(0));
        BroadcastReRenderCommand();

    }

    public void onOpenModalWindowClick(ActionEvent event) {
        TextPropertyDialog textPropertyDialog = new TextPropertyDialog(
                DescriptionTypes.TEXT_DESCR, HouseProject.getInstance().getSelectedItem(), "description");

        Optional<NodeModelContainer> result = textPropertyDialog.Show();

        if (result.isPresent()) {
            EventContextController.RenderAll();
        }


    }

    public void setNewLevelIndex(String value) {
        support.firePropertyChange("levelIndex", "old_value", value);
    }


    ////////////////////////////////////////////////////////////////

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        NodeModelContainer container;
        switch (propertyChangeEvent.getPropertyName()) {
            case "reRender":
                if(propertyChangeEvent.getNewValue() != null && propertyChangeEvent.getNewValue().getClass() == NodeModelContainer.class) {
                    container = (NodeModelContainer) propertyChangeEvent.getNewValue();
                    ChangeableItem = container.getPropertyModel();
                    PropertyView.getChildren().clear();
                    PropertyView.getChildren().add(ChangeableItem.getTreePropertyNode(this).getPropertyNode());
                }
                modelTreeInitialize();
                break;
            case "newSelectedItemOnWorkbench":
                container = (NodeModelContainer) propertyChangeEvent.getNewValue();
                HouseProject.getInstance().setSelectedItem(container);
                ChangeableItem = container.getPropertyModel();
                PropertyView.getChildren().clear();
                PropertyView.getChildren().add(ChangeableItem.getTreePropertyNode(this).getPropertyNode());
                break;
            case "drawWayPointsGraph":
                container = (NodeModelContainer) propertyChangeEvent.getNewValue();
                if(container != null) {
                    HouseProject.getInstance().setSelectedItem(container);
                    ChangeableItem = container.getPropertyModel();
                    PropertyView.getChildren().clear();
                    PropertyView.getChildren().add(ChangeableItem.getGraphPropertyNode().getPropertyNode());
                }
                break;
            default:
                break;
        }
    }

    public void BroadcastReRenderCommand()
    {
        support.firePropertyChange("reRender", null, null);
    }

    /////////////////////////////////////////////////////////////////////////////

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

}
