package nl.levy.COCPlugin.COC;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryHelper {

    public static Inventory createInventory(int size, String title) {
        return Bukkit.createInventory(null, size, title);
    }

    public static void addItem(Inventory inventory, int rows, int columns, ItemStack itemStack) {
        inventory.setItem((rows - 1) * 9 + columns - 1, itemStack);
    }

    public static ItemStack createItem(Material material, String name, int count) {

        ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(count);

        var meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack createItem(Material material, String name) {
        return createItem(material, name, 1);
    }
}
