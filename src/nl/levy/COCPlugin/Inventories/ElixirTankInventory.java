package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.ElixirTank;
import org.bukkit.Material;

public class ElixirTankInventory extends COCStorageInventory{
    public ElixirTankInventory(ElixirTank item) {
        super(item, "Elixir tank", Material.PURPLE_WOOL);
    }
}
