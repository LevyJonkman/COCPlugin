package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.ItemBuilder.BaseLevelData;

public class TownHall extends COCLevelItem{

    private COCResourceStorage Gold;
    private COCResourceStorage Elixir;

    public TownHall(int x, int y, BaseLevelData data) {
        super(x, y, data);

//        Gold = new GoldStorage(x, y,);
    }

    @Override
    public COCInventory createInventory() {
        return null;
    }

    @Override
    public int getStaging() {
        return 0;
    }
}
