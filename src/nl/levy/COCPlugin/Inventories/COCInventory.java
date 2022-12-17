package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public abstract class COCInventory {

    public final Inventory inventory;

    public COCInventory(String name) {
        inventory = InventoryHelper.createInventory(6 * 9, name);
    }

    public void start() {
    }

    public void update() {
    }

    public void clicked(COCManager manager, int slot, HumanEntity player) {
    }

    public boolean isClicked(int row, int column, int clicked) {
        return (row - 1) * 9 + column - 1 == clicked;
    }
}
