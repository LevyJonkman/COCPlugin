package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.ElixirTankInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

public class ElixirTank extends COCResourceStorage {
    public ElixirTank(int x, int y, LevelItemBuilder data, COCManager manager) {
        super(x, y, data.elixirTankData, manager.Elixir);
    }

    @Override
    public COCInventory createInventory() {
        return new ElixirTankInventory(this);
    }
}
