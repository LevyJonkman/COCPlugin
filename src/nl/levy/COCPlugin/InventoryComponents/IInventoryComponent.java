package nl.levy.COCPlugin.InventoryComponents;

import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public interface IInventoryComponent {

    public void start(Inventory inventory);
    public void update(Inventory inventory);

    void clicked(Inventory inventory, COCManager manager, int slot, HumanEntity player);
}
