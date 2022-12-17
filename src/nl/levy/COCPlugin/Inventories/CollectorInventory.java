package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.COCResourceItem;
import org.bukkit.Material;

public class CollectorInventory extends COCResourceInventory {
    public CollectorInventory(COCResourceItem resourceItem) {
        super(resourceItem, "Elixir Collector", Material.PURPLE_WOOL);
    }
}
