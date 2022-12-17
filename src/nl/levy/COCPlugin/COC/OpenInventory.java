package nl.levy.COCPlugin.COC;

import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.Inventories.COCInventory;

public class OpenInventory {
    public OpenInventory(COCItem item, COCInventory inventory) {
        this.item = item;
        this.inventory = inventory;
    }

    public COCItem item;
    public COCInventory inventory;
}
