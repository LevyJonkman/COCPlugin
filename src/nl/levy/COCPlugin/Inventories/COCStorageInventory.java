package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCBuildings.COCResourceStorage;
import org.bukkit.Material;

public class COCStorageInventory extends COCProgressBarInventory {

    private final COCResourceStorage item;
    public COCStorageInventory(COCResourceStorage item, String name, Material material) {
        super(item, name, material);
        this.item = item;
    }

    @Override
    public void update() {
        super.update();

        InventoryHelper.addItem(inventory, 2, 2, InventoryHelper.createItem(Material.IRON_BLOCK, "Holds " + item.totalStorage() + " in total"));
    }

    @Override
    protected int getTotal() {
        return item.totalStorage();
    }

    @Override
    protected int getCurrent() {
        return item.currentStorage();
    }
}
