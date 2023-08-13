package com.example.demofx.Modules.ModelNavigator;

import com.example.demofx.Interfaces.IPropertyChangeble;
import javafx.scene.control.TreeItem;

public class TreeItemContainer<T> extends TreeItem {

    private IPropertyChangeble model;

    public TreeItemContainer(T value, IPropertyChangeble model) {
        super(value.toString());
        this.model = model;
    }

    public void setModel(IPropertyChangeble item){
        this.model = item;
    }

    public IPropertyChangeble getModel() {
        return model;
    }

}
