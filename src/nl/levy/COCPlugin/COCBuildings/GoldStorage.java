package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.GoldStorageInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

public class GoldStorage extends COCResourceStorage {
    public GoldStorage(int x, int y, LevelItemBuilder data, COCManager manager) {
        super(x, y, data.goldStorageData, manager.Gold);
    }

    @Override
    public COCInventory createInventory() {
        return new GoldStorageInventory(this);
    }
}
