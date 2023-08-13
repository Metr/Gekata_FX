package com.example.demofx.Utils.Events;

import com.example.demofx.Modules.ItemList.ItemListProvider;
import com.example.demofx.Modules.ModelNavigator.ModelTreeProvider;
import com.example.demofx.Modules.TopMenu.TopMenuProvider;
import com.example.demofx.Modules.Workbench.WorkbenchProvider;
import com.example.demofx.Utils.Aggregators.LevelButtonsAggregator;

public class EventContextController {

    private static EventContextController EventsController;

    private static WorkbenchProvider workbenchProvider;
    private static ItemListProvider itemListProvider;
    private static ModelTreeProvider modelTreeProvider;

    private static TopMenuProvider topMenuProvider;

    public static synchronized EventContextController getInstance() {
        if (EventsController == null) {
            EventsController = new EventContextController();

        }
        return EventsController;
    }

    public void setProviders(WorkbenchProvider _workbenchProvider, ItemListProvider _itemListProvider, ModelTreeProvider _modelTreeProvider, TopMenuProvider _topMenuProvider)    {
        workbenchProvider = _workbenchProvider;
        itemListProvider = _itemListProvider;
        modelTreeProvider = _modelTreeProvider;
        topMenuProvider = _topMenuProvider;

        itemListProvider.addPropertyChangeListener(workbenchProvider);
        itemListProvider.addPropertyChangeListener(modelTreeProvider);
        itemListProvider.addPropertyChangeListener(topMenuProvider);

        workbenchProvider.addPropertyChangeListener(itemListProvider);
        workbenchProvider.addPropertyChangeListener(modelTreeProvider);
        workbenchProvider.addPropertyChangeListener(topMenuProvider);

        modelTreeProvider.addPropertyChangeListener(workbenchProvider);
        modelTreeProvider.addPropertyChangeListener(itemListProvider);
        modelTreeProvider.addPropertyChangeListener(topMenuProvider);

        topMenuProvider.addPropertyChangeListener(workbenchProvider);
        topMenuProvider.addPropertyChangeListener(itemListProvider);
        topMenuProvider.addPropertyChangeListener(modelTreeProvider);
    }

   public static void RenderAll(){
        workbenchProvider.BroadcastReRenderCommand();
        itemListProvider.BroadcastReRenderCommand();
   }

    public static WorkbenchProvider getWorkbenchProvider() {
        return workbenchProvider;
    }

    public static ItemListProvider getItemListProvider() {
        return itemListProvider;
    }

    public static ModelTreeProvider getModelTreeProvider() {
        return modelTreeProvider;
    }
}
