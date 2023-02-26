package nl.levy.COCPlugin.InventoryComponents;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Components.ResourceGeneratorComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.InventoryComponents.IInventoryComponent;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class ResourceComponent implements IInventoryComponent {
    private final ResourceGeneratorComponent resourceGeneratorComponent;
    private final Material collectMaterial;

    public ResourceComponent(ResourceGeneratorComponent resourceGeneratorComponent, Material collectMaterial) {
        this.resourceGeneratorComponent = resourceGeneratorComponent;
        this.collectMaterial = collectMaterial;
    }

    @Override
    public void start(Inventory inventory) {

    }

    @Override
    public void update(Inventory inventory) {

        if (resourceGeneratorComponent.getProduction() > 0) {
            InventoryHelper.addItem(inventory, 2, 2, InventoryHelper.createItem(collectMaterial, "Collect"));
        } else {
            InventoryHelper.addItem(inventory, 2, 2, InventoryHelper.createItem(Material.GRAY_WOOL, "Collect"));
        }
        var cur = resourceGeneratorComponent.getCurrentProductionLevel();

        InventoryHelper.addItem(inventory, 3, 2, InventoryHelper.createItem(Material.IRON_BLOCK, "Produces " + cur.productionPerSeconde + " per second"));
        InventoryHelper.addItem(inventory, 4, 2, InventoryHelper.createItem(Material.IRON_BLOCK, "Holds " + cur.total + " in total"));
    }

    @Override
    public void clicked(Inventory inventory, COCManager manager, int slot, HumanEntity player) {
        if (COCInventory.isClicked(2, 2, slot)) {
            resourceGeneratorComponent.collect(manager);
        }
    }
}
