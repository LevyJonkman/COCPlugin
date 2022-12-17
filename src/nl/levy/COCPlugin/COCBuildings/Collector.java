package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.CollectorInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

public class Collector extends COCResourceItem {

    public Collector(int x, int y, LevelItemBuilder builder) {
        super(x, y, builder.elixirCollectorData);
    }

    @Override
    public COCInventory createInventory() {
        return new CollectorInventory(this);
    }

    @Override
    protected void addResourcesToPlayer(COCManager manager, int amount) {
        manager.addElixir(amount);
    }
}
