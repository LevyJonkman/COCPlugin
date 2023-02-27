package nl.levy.COCPlugin.Save;


import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCManager.COCManager;

import java.util.ArrayList;

public class SaveCOCManager {
    public final int Gold;
    public final int Elixir;
    public final int DarkElixir;
    public final ArrayList<SaveCOCItem> items;

    SaveCOCManager(int gold, int elixir, int darkElixir, ArrayList<SaveCOCItem> items) {
        Gold = gold;
        Elixir = elixir;
        DarkElixir = darkElixir;
        this.items = items;
    }

    public static SaveCOCManager from(COCManager manager) {
        var rm = manager.resourceManger;
        return new SaveCOCManager(
                rm.getCurrent(ResourceType.Gold),
                rm.getCurrent(ResourceType.Elixir),
                rm.getCurrent(ResourceType.DarkElixir),
                SaveCOCItem.from(manager.COCItems)
        );
    }
}