package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COC.OpenInventory;
import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCItems.COCMainManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private final List<OpenInventory> openInventories;
    private final COCMainManager cocMainManager;

    public InventoryManager(COCMainManager cocMainManager) {
        this.cocMainManager = cocMainManager;
        openInventories = new ArrayList<>();
    }

    public void update() {
        for (OpenInventory openInventory : openInventories) {
            openInventory.inventory.update();
        }
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
            openInventory.inventory.clicked(cocMainManager.getManager(player), slot, player);

            return true;
        }

        return false;
    }
}
