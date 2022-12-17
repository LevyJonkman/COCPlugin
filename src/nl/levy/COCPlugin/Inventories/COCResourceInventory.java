package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCBuildings.COCResourceItem;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

public class COCResourceInventory extends COCProgressBarInventory {
    private final COCResourceItem resourceItem;
    private final Material material;

    public COCResourceInventory(COCResourceItem resourceItem, String name, Material material) {
        super(resourceItem, name, material);
        this.resourceItem = resourceItem;
        this.material = material;
    }

    @Override
    public void clicked(COCManager manager, int slot, HumanEntity player) {
        super.clicked(manager, slot, player);

        if (isClicked(2, 2, slot)) {
            resourceItem.collect(manager);
            update();
        }
    }

    @Override
    public void update() {
        super.update();

        if (resourceItem.getProduction() > 0) {
            InventoryHelper.addItem(inventory, 2, 2, InventoryHelper.createItem(material, "Collect"));
        } else {
            InventoryHelper.addItem(inventory, 2, 2, InventoryHelper.createItem(Material.GRAY_WOOL, "Collect"));
        }
        var cur = resourceItem.getCurrentLevel();

        InventoryHelper.addItem(inventory, 3, 2, InventoryHelper.createItem(Material.IRON_BLOCK, "Produces " + cur.productionPerSeconde + " per second"));
        InventoryHelper.addItem(inventory, 4, 2, InventoryHelper.createItem(Material.IRON_BLOCK, "Holds " + cur.total + " in total"));
    }

    @Override
    protected int getTotal() {
        return resourceItem.getCurrentLevel().total;
    }

    @Override
    protected int getCurrent() {
        return resourceItem.getProduction();
    }
}
