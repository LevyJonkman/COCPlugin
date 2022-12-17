package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

public class COCLevelInventory extends COCInventory {

    private final COCLevelItem item;

    public COCLevelInventory(COCLevelItem item, String name) {
        super(name);
        this.item = item;
    }

    @Override
    public void update() {
        super.update();

        InventoryHelper.addItem(inventory, 2, 7,
                InventoryHelper.createItem(Material.IRON_BLOCK, "Level " + item.level));
        if (item.notMaxLevel()) {
            InventoryHelper.addItem(inventory, 3, 7,
                    InventoryHelper.createItem(Material.GOLD_BLOCK, "Upgrade to level " + (item.level + 1)));
        } else {
            InventoryHelper.addItem(inventory, 3, 7, InventoryHelper.createItem(Material.GRAY_WOOL, "Max level"));
        }
    }

    @Override
    public void clicked(COCManager manager, int slot, HumanEntity player) {
        super.clicked(manager, slot, player);

        if (isClicked(3, 7, slot)) {
            if (item.notMaxLevel()) {
                if (manager.hasResources(item.getNextLevelPrice())) {
                    manager.removeResources(item.getNextLevelPrice());
                    item.upgrade();
                    update();
                } else
                    player.sendMessage("Not enough resources");
            } else
                player.sendMessage("Already max level");
        }
    }
}
