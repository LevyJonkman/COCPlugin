package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.COCLocation;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.ItemBuilder.BaseData;

public abstract class COCItem {
    public COCLocation location;
    public int size;

    public COCItem(int x, int y, BaseData data) {
        this.location = new COCLocation(x, y);
        this.size = data.size;
    }

    public abstract COCInventory createInventory();

    public abstract int getStaging();
}
