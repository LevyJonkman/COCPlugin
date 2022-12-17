package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COC.OpenInventory;
import nl.levy.COCPlugin.COCBuildings.COCItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager extends BuildingManager{
    public final Runnable runnable;
    public final List<OpenInventory> openInventories;

    public InventoryManager() {
        openInventories = new ArrayList<>();
        runnable = () -> {
            for (var inv: openInventories) {
                inv.inventory.update();
            }
        };
    }


    public void OpenInventory(Player player, COCItem item) {
        var inv = item.createInventory();
        inv.start();
        inv.update();

        openInventories.add(new OpenInventory(item, inv));

        player.openInventory(inv.inventory);
    }

    private OpenInventory getInventory(Inventory inventory) {
        OpenInventory openInventory = null;

        for (var inv: openInventories) {
            if (inv.inventory.inventory == inventory) {
                openInventory = inv;
                break;
            }
        }

        return openInventory;
    }

    public void closeInventory(Inventory inventory) {
        var openInventory = getInventory(inventory);

        if (openInventory!=null) {
            openInventories.remove(openInventory);
        }
    }

    public boolean clickedInventoryItem(Inventory inventory, int slot, HumanEntity player) {
        var openInventory = getInventory(inventory);


        if (openInventory!=null) {
            openInventory.inventory.clicked((COCManager) this, slot, player);

            return true;
        }

        return false;
    }
}
