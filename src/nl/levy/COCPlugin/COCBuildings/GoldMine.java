package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.GoldMineInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

public class GoldMine extends COCResourceItem {

    public GoldMine(int x, int y, LevelItemBuilder builder) {
        super(x, y, builder.goldMineData);
    }

    @Override
    public COCInventory createInventory() {
        return new GoldMineInventory(this);
    }

    @Override
    protected void addResourcesToPlayer(COCManager manager, int amount) {
        manager.addGold(amount);
    }
}
