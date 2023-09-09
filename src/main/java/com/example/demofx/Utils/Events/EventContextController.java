package com.example.demofx.Utils.Events;

import com.example.demofx.Main;
import com.example.demofx.Models.Basic.HouseProject;
import com.example.demofx.Modules.BottomOutput.BottomOutputProvider;
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
    private static BottomOutputProvider bottomOutputProvider;

    public static synchronized EventContextController getInstance() {
        if (EventsController == null) {
            EventsController = new EventContextController();

        }
        return EventsController;
    }

    public void setProviders(WorkbenchProvider _workbenchProvider, ItemListProvider _itemListProvider,
                             ModelTreeProvider _modelTreeProvider, TopMenuProvider _topMenuProvider,
                             BottomOutputProvider _bottomOutputProvider)    {
        workbenchProvider = _workbenchProvider;
        itemListProvider = _itemListProvider;
        modelTreeProvider = _modelTreeProvider;
        topMenuProvider = _topMenuProvider;
        bottomOutputProvider = _bottomOutputProvider;

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
       Main.getpStage().setTitle("Project GEKATA: " + HouseProject.getInstance().getProjectName());
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

    public static TopMenuProvider getTopMenuProvider() {
        return topMenuProvider;
    }

    public static BottomOutputProvider getBottomOutputProvider() {
        return bottomOutputProvider;
    }
}
