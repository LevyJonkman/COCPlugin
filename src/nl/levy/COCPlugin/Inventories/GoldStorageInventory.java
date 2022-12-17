package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.GoldStorage;
import org.bukkit.Material;

public class GoldStorageInventory extends COCStorageInventory{
    public GoldStorageInventory(GoldStorage item) {
        super(item, "Gold storage", Material.YELLOW_WOOL);
    }
}
