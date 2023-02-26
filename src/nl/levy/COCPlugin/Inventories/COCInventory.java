package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.InventoryComponents.IInventoryComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class COCInventory {

    public final Inventory inventory;

    public final List<IInventoryComponent> components;

    public COCInventory(String name) {
        inventory = InventoryHelper.createInventory(6 * 9, name);
        components = new ArrayList<>();
    }

    public void start() {
        for (IInventoryComponent component : components) {
            component.start(inventory);
        }
    }

    public void update() {
        for (IInventoryComponent component : components) {
            component.update(inventory);
        }
    }

    public void clicked(COCManager manager, int slot, HumanEntity player) {
        for (IInventoryComponent component : components) {
            component.clicked(inventory, manager, slot, player);
        }
    }

    public static boolean isClicked(int row, int column, int clicked) {
        return (row - 1) * 9 + column - 1 == clicked;
    }
}
