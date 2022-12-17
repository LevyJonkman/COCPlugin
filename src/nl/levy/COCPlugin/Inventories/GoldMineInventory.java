package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.GoldMine;
import org.bukkit.Material;

public class GoldMineInventory extends COCResourceInventory {
    public GoldMineInventory(GoldMine goldMine) {
        super(goldMine,"Goldmine", Material.YELLOW_WOOL);
    }
}
